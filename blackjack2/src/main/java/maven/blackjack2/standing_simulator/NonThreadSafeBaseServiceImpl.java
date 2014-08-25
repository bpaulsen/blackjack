package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import maven.blackjack2.standing_simulator.NonThreadSafeBaseImpl;

public class NonThreadSafeBaseServiceImpl implements StandingSimulatorService {
	NonThreadSafeBaseServiceImpl() {}
	
	@Override
	public StandingServiceImpl standingServiceImpl( Hand player, Hand dealer, Deck deck ) {
		return new NonThreadSafeBaseImpl( player, dealer, deck );
	}
}
