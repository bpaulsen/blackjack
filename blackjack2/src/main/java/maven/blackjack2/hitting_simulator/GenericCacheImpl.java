package maven.blackjack2.hitting_simulator;

import maven.blackjack2.Deck;
import maven.blackjack2.GenericCache;
import maven.blackjack2.Hand;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class GenericCacheImpl extends NonThreadSafeBaseImpl implements HittingSimulatorService  {
	private static GenericCache<Long, Double> expected_return_cache = new GenericCache<>();
	
	public GenericCacheImpl() {}
	
	/* (non-Javadoc)
	 * @see HittingSimulator#expected_return()
	 */
	@Override
	public double expected_return(Hand player, Hand dealer, Deck deck) {
		try {
			return expected_return_cache.getValue(hash_key(player, dealer), new Callable<Double>() {
			      @Override
			      public Double call() throws Exception {
			    	  return expected_return_calc(player, dealer, deck);
			      }
			} );
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0; 
	}
}


