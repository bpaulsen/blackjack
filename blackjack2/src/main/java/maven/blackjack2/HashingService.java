package maven.blackjack2;

/**
 * Date: 8/25/14
 * Time: 11:18 PM
 */
public interface HashingService {
    public long hash_key(final Hand player, final Hand dealer);
}
