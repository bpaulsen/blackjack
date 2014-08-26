package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class NonThreadSafeBaseImpl implements
		StandingSimulatorService {
	private static final double WIN = 1.0;
	private static final double LOSS = -1.0;
	private static final double PUSH = 0.0;
	private static final double BLACKJACK = 1.5;
	
	@Override
	public double expected_return(final Hand player, final Hand dealer, final Deck deck) {
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
				win_percentage += expected_return(player, dealer, deck) * card_count;
				deck.undeal_card(dealer, i);
			}
		}

		return win_percentage /= deck.count() - skip_count;
	}

}
	
