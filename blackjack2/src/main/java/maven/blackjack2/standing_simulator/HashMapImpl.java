package maven.blackjack2.standing_simulator;

import java.util.HashMap;
import java.util.Map;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class HashMapImpl extends NonThreadSafeBaseImpl implements StandingServiceImpl {
	private static Map<Long, Double> expected_return_cache = new HashMap<Long, Double>(3000000);
	
	public HashMapImpl(Hand player, Hand dealer, Deck deck) {
		super( player, dealer, deck);
	}
	
	@Override
	public double expected_return() {
		Long hash_key = hash_key();
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return_cache.putIfAbsent(hash_key, expected_return_calc());
		}
		return expected_return_cache.get(hash_key);
	}
}
