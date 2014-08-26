package maven.blackjack2.standing_simulator;
import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

import maven.blackjack2.HashingService;
import maven.blackjack2.hitting_simulator.HittingSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gnu.trove.map.*;
import gnu.trove.map.hash.TLongDoubleHashMap;

@Component("StandingSimulator")
public class TroveImpl implements CachingStandingSimulatorService {
    private final HashingService hashingService;
    private final StandingSimulatorService delegate;
	private static TLongDoubleMap expected_return_cache = new TLongDoubleHashMap(3000000);	

    @Autowired
    //  The constructor way
    public TroveImpl(final HashingService hashingService, final @Qualifier("StandingSimulator") StandingSimulatorService standingSimulator) {
        this.hashingService = hashingService;
        this.delegate = standingSimulator;
    }

	@Override
	public double expected_return(Hand player, Hand dealer, Deck deck) {
		long hash_key = hashingService.hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			double expected_return = delegate.expected_return(player, dealer, deck);
			expected_return_cache.put(hash_key, expected_return);
			return expected_return;
		}

		return expected_return_cache.get(hash_key);
	}
}
