package maven.blackjack2.standing_simulator;
import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

public class TroveImpl extends CachingBaseImpl implements StandingSimulatorService {
   	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	

	@Override
	public double expected_return(final Hand player, final Hand dealer, final Deck deck) {
		long hash_key = hashingService.hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			double expected_return = super.expected_return(player, dealer, deck);
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}

		return expected_return_cache.get(hash_key);
	}
}
