package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import maven.blackjack2.standing_simulator.*;

import org.springframework.beans.factory.annotation.Autowired;

public class NonThreadSafeBaseImpl implements HittingSimulatorService {
	private static final double LOSS = -1.0;

	
	@Autowired
	private StandingSimulatorService standing_simulator;
	
	@Override
	public double expected_return(Hand player, Hand dealer, Deck deck) {
		return expected_return_calc(player, dealer, deck);
	}

	protected double expected_return_calc(Hand player, Hand dealer, Deck deck) {
		if (player.count() > 21) {
			return LOSS;
		}

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
				double hit_return = expected_return(player, dealer, deck);
				double stand_return = standing_simulator.expected_return(player, dealer, deck);
				win_percentage += card_count * (hit_return > stand_return ? hit_return : stand_return); 
				deck.undeal_card(player, i);
			}
		}
		
		return win_percentage /= deck.count();
	}
	
	protected long hash_key(Hand player, Hand dealer) {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
}
