package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class RecursiveTaskService implements StandingSimulatorService {
	RecursiveTaskService() {}
	
	@Override
	public StandingServiceImpl standingServiceImpl(Hand player, Hand dealer,
			Deck deck) {
		// TODO Auto-generated method stub
		return new RecursiveTaskImpl( player, dealer, deck );
	}

}
