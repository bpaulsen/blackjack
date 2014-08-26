package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;
import maven.blackjack2.HashingService;
import maven.blackjack2.standing_simulator.CachingStandingSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("trowHitting")
public class TroveImpl implements CachingHittingSimulatorService {
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(75000);

    @Autowired
    @Qualifier("HittingSimulator")
    protected HittingSimulatorService delegate;

    @Autowired
    protected HashingService hashingService;

	@Override
	public double expected_return(final Hand player, final Hand dealer, final Deck deck) {
		long hash_key = hashingService.hash_key(player, dealer);
		double expected_return;
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return = delegate.expected_return(player, dealer, deck);
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}
		return expected_return_cache.get(hash_key);
	}
}
