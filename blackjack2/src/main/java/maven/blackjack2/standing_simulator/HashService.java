package maven.blackjack2.standing_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class HashService implements StandingSimulatorService {
	HashService() {};
	
	@Override
	public StandingServiceImpl standingServiceImpl(Hand player, Hand dealer, Deck deck) {
		return new HashMapImpl(player, dealer, deck);
	}
}
