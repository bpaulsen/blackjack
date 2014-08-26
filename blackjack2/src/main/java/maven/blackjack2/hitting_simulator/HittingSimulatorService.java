package maven.blackjack2.hitting_simulator;
import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public interface HittingSimulatorService {
	public double expected_return(Hand player, Hand dealer, Deck deck);
}
