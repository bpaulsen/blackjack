package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import maven.blackjack2.standing_simulator.*;

import org.springframework.beans.factory.annotation.Autowired;

public class NonThreadSafeBaseImpl implements HittingSimulatorService {
	private static final double LOSS = -1.0;
	
	private Hand player;
	private Hand dealer;
	private Deck deck;
	
	@Autowired
	private StandingSimulatorService standing_simulator;

	public NonThreadSafeBaseImpl(Hand player, Hand dealer, Deck deck ) {
		this.player = player;
		this.dealer = dealer;
		this.deck = deck;
	}
	
	@Override
	public double expected_return() {
		return expected_return_calc();
	}

	protected double expected_return_calc() {
		if (player.count() > 21) {
			return LOSS;
		}

		standing_simulator.setDealer(dealer);
		int hard_count = player.hard_count();
		Double win_percentage = 0.0;
		for (int i=10; i > 0; --i) {
			int card_count = deck.count(i);
			if (card_count == 0) {
				continue;
			}
	
			if (hard_count + i > 21) {
				win_percentage -= card_count;
			}
			else {
				deck.deal_card(player, i);
				double hit_return = expected_return();
				standing_simulator.setDeck(deck);
				standing_simulator.setPlayer(player);
				double stand_return = standing_simulator.expected_return();
				win_percentage += card_count * (hit_return > stand_return ? hit_return : stand_return); 
				deck.undeal_card(player, i);
			}
		}
		
		return win_percentage /= deck.count();
	}
	
	protected long hash_key() {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
}
