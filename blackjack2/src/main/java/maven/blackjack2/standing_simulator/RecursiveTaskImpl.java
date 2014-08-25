package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.GenericCache;
import maven.blackjack2.Hand;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskImpl extends RecursiveTask<Double> implements StandingSimulatorService  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hand player;
	private Hand dealer;
	private Deck deck;	
	
	private static final double WIN = 1.0;
	private static final double LOSS = -1.0;
	private static final double PUSH = 0.0;
	private static final double BLACKJACK = 1.5;
	private static final GenericCache<Long, Double> expected_return_cache = new GenericCache<>();
	
	public RecursiveTaskImpl(Hand player, Hand dealer, Deck deck ) {
		this.player = player.clone();
		this.dealer = dealer.clone();
		this.deck = deck.clone();
	}
	
	@Override
	public double expected_return() {
		return compute();
	}
	
	protected Double compute() { 	
		try {
			return expected_return_cache.getValue(hash_key(), new Callable<Double>() {
			      @Override
			      public Double call() throws Exception {
			  		 return expected_return_calc();
			      }
			} );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	protected double expected_return_calc() {
		int player_count = player.count();

		if (player.blackjack() && dealer.card_count() >= 2 ) {
			return dealer.blackjack() ? PUSH : BLACKJACK;
		}
		if (player_count > 21) { // player busts at 21
			return LOSS;
		}
		int dealer_count = dealer.count();
		if (dealer_count > 21) { // dealer busts at 21
			return WIN;
		}

		// TODO - need to handle blackjacks
		int hard_count = dealer.hard_count();
		boolean dealer_hits_on_soft_17 = false;
		if ( dealer_count >= 17 && !( dealer_hits_on_soft_17 && dealer_count == 17 && dealer_count != dealer.hard_count() ) ) { // handle wins, losses, and pushes			
			if ( dealer_count > player_count ) {
				return LOSS;
			}
			else if ( dealer_count == player_count ) {
				return dealer.blackjack() ? LOSS : PUSH;
			}
			else {
				return WIN;
			}
		}

		// dealer has less than 17, now calculate win percentage of dealer getting new cards
		double[] win_percentages = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		RecursiveTaskImpl[] simulators = new RecursiveTaskImpl[11];

		double win_percentage = 0.0;
		int skip_count = 0;
		for (int i=10; i > 0; --i) {
			int card_count = deck.count(i);
			if (card_count == 0) {
				continue;
			}
			if ( dealer.card_count() == 1
					&& ( ( hard_count == 10 && i == 1 ) || ( hard_count == 1 && i == 10 ) ) ) {
				skip_count = card_count;	
				continue;	
			}

			if (hard_count + i > 21) {
				win_percentage += card_count;
			}
			else if ( hard_count + i >= 17 ) {
				if ( hard_count + i > player_count ) {
					win_percentage -= card_count;
				}
				else if ( hard_count + i < player_count ) {
					win_percentage += card_count;
				}
			}
			else {
				deck.deal_card(dealer, i);
				win_percentages[i] = 1.0 * card_count;
				simulators[i] = new RecursiveTaskImpl(player, dealer, deck);
				simulators[i].fork();
				// win_percentage += expected_return() * card_count;
				deck.undeal_card(dealer, i);
			}
		}

		for (int i=10; i > 0; --i) {		
			if (simulators[i] != null) {
				win_percentage += win_percentages[i] * simulators[i].join();
			}
		}
		
		return win_percentage /= deck.count() - skip_count;
	}
	
	protected long hash_key() {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
}
