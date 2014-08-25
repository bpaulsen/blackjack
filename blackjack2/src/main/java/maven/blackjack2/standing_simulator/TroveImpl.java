package maven.blackjack2.standing_simulator;
import org.springframework.stereotype.Component;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

@Component("StandingSimulator")
public class TroveImpl extends NonThreadSafeBaseImpl implements StandingSimulatorService {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	
	
	public TroveImpl() {}
	
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
