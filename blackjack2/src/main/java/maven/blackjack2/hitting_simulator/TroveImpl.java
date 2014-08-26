package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

public class TroveImpl extends NonThreadSafeBaseImpl implements HittingSimulatorService {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(75000);	
	
	public TroveImpl() {}
	
	@Override
	public double expected_return(Hand player, Hand dealer, Deck deck) {
		long hash_key = hash_key(player, dealer);
		double expected_return;
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return = expected_return_calc(player, dealer, deck);
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}
		return expected_return_cache.get(hash_key);
	}
}
