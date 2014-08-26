package maven.blackjack2.standing_simulator;

import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public interface StandingSimulatorService {
	double expected_return(Hand player, Hand dealer, Deck deck);
}
