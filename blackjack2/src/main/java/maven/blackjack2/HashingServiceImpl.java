package maven.blackjack2;

import org.springframework.stereotype.Component;

@Component
public class HashingServiceImpl implements HashingService {
    public long hash_key(final Hand player, final Hand dealer) {
        return player.hashCode() | (((long) dealer.hashCode()) << 31);
    }
}
