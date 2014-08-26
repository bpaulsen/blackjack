package maven.blackjack2.standing_simulator;

import java.util.HashMap;
import java.util.Map;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class HashMapImpl extends CachingBaseImpl implements StandingSimulatorService {
	private static Map<Long, Double> expected_return_cache = new HashMap<Long, Double>(3000000);

	@Override
	public double expected_return(final Hand player, final Hand dealer, final Deck deck) {
		Long hash_key = hashingService.hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			double expected_return = super.expected_return(player, dealer, deck);
			expected_return_cache.putIfAbsent(hash_key, expected_return);
			return expected_return;
		}
		return expected_return_cache.get(hash_key);
	}
}
