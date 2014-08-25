package maven.blackjack2.standing_simulator;

import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public interface StandingSimulatorService {
	void setPlayer(Hand hand);
	void setDealer(Hand hand);
	void setDeck(Deck deck);
	double expected_return();
}
