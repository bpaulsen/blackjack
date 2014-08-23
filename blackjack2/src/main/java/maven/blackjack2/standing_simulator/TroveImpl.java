package maven.blackjack2.standing_simulator;
import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;
import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

public class TroveImpl extends NonThreadSafeBaseImpl implements StandingSimulator {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	
	
	public TroveImpl( Hand player, Hand dealer, Deck deck ) {
		super( player, dealer, deck );
	}
	
	@Override
	public double expected_return() {
		long hash_key = hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			double expected_return = expected_return_calc();
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}

		return expected_return_cache.get(hash_key);
	}
}
