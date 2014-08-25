package maven.blackjack2.standing_simulator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;
import maven.blackjack2.Hand;
import maven.blackjack2.Deck;

@Service("StandingSimulator")
@Scope(value = "prototype")
public class TroveImpl extends NonThreadSafeBaseImpl implements StandingSimulatorService {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	
	
	public TroveImpl( Hand player, Hand dealer, Deck deck ) {
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
