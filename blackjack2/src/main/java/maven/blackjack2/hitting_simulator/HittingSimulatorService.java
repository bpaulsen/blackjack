package maven.blackjack2.hitting_simulator;
import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public interface HittingSimulatorService {
	void setPlayer(Hand hand);
	void setDealer(Hand hand);
	void setDeck(Deck deck);
	public double expected_return();
}
