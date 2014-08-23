package maven.blackjack2.standing_simulator;

import java.util.HashMap;
import java.util.Map;
import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public class HashMapImpl extends NonThreadSafeBaseImpl implements StandingSimulator {
	private static Map<Long, Double> expected_return_cache = new HashMap<Long, Double>(3000000);
	
	public HashMapImpl( Hand player, Hand dealer, Deck deck ) {
		super( player, dealer, deck );
	}
	
	@Override
	public double expected_return() {
		Long hash_key = Hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return_cache.putIfAbsent(hash_key, expected_return_calc());
		}
		return expected_return_cache.get(hash_key);
	}
	
	@Override
	public Double Expected_return() {
		Long hash_key = Hash_key(player, dealer);
		Double expected_return = expected_return_cache.get(hash_key);
		if ( expected_return == null ) {
			expected_return = expected_return_calc();
			expected_return_cache.put(hash_key, expected_return);
		}
		
		return expected_return;
	}
}
