package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.GenericCache;
import maven.blackjack2.Hand;
import maven.blackjack2.standing_simulator.*;

import java.util.concurrent.*;

public class RecursiveTaskImpl extends RecursiveTask<Double> implements HittingSimulatorService  {
	Hand player;
	Hand dealer;
	Deck deck;
	
	private static final Double WIN = 1.0;
	private static final Double LOSS = -1.0;
	private static final Double PUSH = 0.0;
	private static final Double BLACKJACK = 1.5;
	
	private static GenericCache<Long, Double> expected_return_cache = new GenericCache<>();
	
	public RecursiveTaskImpl(Hand player, Hand dealer, Deck deck ) {
		this.player = player.clone();
		this.dealer = dealer.clone();
		this.deck = deck.clone();
	}
	
	protected Double compute() {
		try {
			return expected_return_cache.getValue(hash_key(), new Callable<Double>() {
			      @Override
			      public Double call() throws Exception {
			    	  return expected_return_calc();
			      }
			} );
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	/* (non-Javadoc)
	 * @see HittingSimulator#expected_return()
	 */
	@Override
	public double expected_return() {
		return compute();	
	}
	
	private double expected_return_calc() {
  		int player_count = player.count();
		if (player_count > 21) { // player busts at 21
			return LOSS;
		}
		
		int hard_count = player.hard_count();
		
		double[] hit_percentages = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		double[] stand_percentages = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		double[] win_percentages = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		maven.blackjack2.standing_simulator.RecursiveTaskImpl[] stand_simulators = new maven.blackjack2.standing_simulator.RecursiveTaskImpl[11];
		RecursiveTaskImpl[] hit_simulators = new RecursiveTaskImpl[11];
		for (int i=10; i>0; --i) {
			hit_percentages[i] = stand_percentages[i] = deck.count(i);
			if ( hit_percentages[i] == 0) {
				continue;
			}
			
			if (hard_count + i > 21 ) {
				win_percentages[i] = -1 * hit_percentages[i];
				continue;
			}
				
			deck.deal_card(player, i);
			stand_simulators[i] = new maven.blackjack2.standing_simulator.RecursiveTaskImpl(player, dealer, deck);
			hit_simulators[i] = new RecursiveTaskImpl(player, dealer, deck);
			hit_simulators[i].fork();
			stand_simulators[i].fork();
			deck.undeal_card(player, i);
		}

		for (int i=1; i <= 10; i++) {		
			if (hit_simulators[i] != null) {
				hit_percentages[i] *= hit_simulators[i].join();
				stand_percentages[i] *= stand_simulators[i].join();		
				win_percentages[i] = hit_percentages[i] > stand_percentages[i] ? hit_percentages[i] : stand_percentages[i];
			}
		}
		
		double win_percentage = 0;
		for (int i=10; i > 0; --i) {
			win_percentage += win_percentages[i];
		}
		
		return win_percentage /= deck.count();
	}
	
	protected long hash_key() {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
}


