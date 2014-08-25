package maven.blackjack2.standing_simulator;
import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

public class TroveImpl extends NonThreadSafeBaseImpl implements StandingServiceImpl {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	
	
	public TroveImpl(Hand player, Hand dealer, Deck deck) {
		super( player, dealer, deck );
	}
	
	@Override
	public double expected_return() {
		long hash_key = hash_key();
		if ( !expected_return_cache.containsKey(hash_key) ) {
			double expected_return = expected_return_calc();
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}

		return expected_return_cache.get(hash_key);
	}
}
