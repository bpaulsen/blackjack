package maven.blackjack2.standing_simulator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import maven.blackjack2.GenericCache;
import maven.blackjack2.Deck;
import maven.blackjack2.Hand;

public class GenericCacheImpl extends NonThreadSafeBaseImpl implements StandingSimulator {
	private static GenericCache<Long, Double> expected_return_cache = new GenericCache<>();

	public GenericCacheImpl( Hand player, Hand dealer, Deck deck ) {
		super( player, dealer, deck );
	}
	
	@Override
	public double expected_return() {
		try {
			return expected_return_cache.getValue(hash_key(player, dealer), new Callable<Double>() {
			      @Override
			      public Double call() throws Exception {
			    	  return expected_return_calc();
			      }
			} );
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0.0;
		}
	}
}
