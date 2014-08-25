package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class GenericCacheService implements StandingSimulatorService {
	GenericCacheService() {}
	
	@Override
	public StandingServiceImpl standingServiceImpl(Hand player, Hand dealer,
			Deck deck) {
		return new GenericCacheImpl( player, dealer, deck );
	}

}
