package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.GenericCache;
import maven.blackjack2.Hand;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class GenericCacheImpl extends CachingBaseImpl implements HittingSimulatorService  {
	private static GenericCache<Long, Double> expected_return_cache = new GenericCache<>();

	@Override
	public double expected_return(final Hand player, final Hand dealer, final Deck deck) {
		try {
			return expected_return_cache.getValue(hashingService.hash_key(player, dealer), new Callable<Double>() {
			      @Override
			      public Double call() throws Exception {
			    	  // not sure why I can't call super.expected_return here
			    	  return maven.blackjack2.hitting_simulator.GenericCacheImpl.super.expected_return(player, dealer, deck);
			      }
			} );
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0; 
	}
}


