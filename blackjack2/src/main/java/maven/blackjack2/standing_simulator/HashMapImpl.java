package maven.blackjack2.standing_simulator;

import java.util.HashMap;
import java.util.Map;

import maven.blackjack2.Deck;
import maven.blackjack2.Hand;
import maven.blackjack2.HashingService;
import org.springframework.beans.factory.annotation.Autowired;

public class HashMapImpl extends NonThreadSafeBaseImpl implements StandingSimulatorService {
	private static Map<Long, Double> expected_return_cache = new HashMap<Long, Double>(3000000);

    @Autowired
    // The member way
    protected HashingService hashingService;


	@Override
	public double expected_return(Hand player, Hand dealer, Deck deck) {
		Long hash_key = hashingService.hash_key(player, dealer);
		if ( !expected_return_cache.containsKey(hash_key) ) {
			expected_return_cache.putIfAbsent(hash_key, expected_return_calc(player, dealer, deck));
		}
		return expected_return_cache.get(hash_key);
	}
}
