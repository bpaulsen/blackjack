package maven.blackjack2.hitting_simulator;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

public class TroveImpl extends NonThreadSafeBaseImpl implements HittingSimulatorService {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(75000);	
	
	public TroveImpl() {}
	
	@Override
	public double expected_return() {
		long hash_key = hash_key();
		double expected_return;
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return = expected_return_calc();
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}
		return expected_return_cache.get(hash_key);
	}
}
