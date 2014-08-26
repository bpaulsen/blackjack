package maven.blackjack2.standing_simulator;

import maven.blackjack2.HashingService;

import org.springframework.beans.factory.annotation.Autowired;

public class CachingBaseImpl extends NonThreadSafeBaseImpl {
    @Autowired
    // The member way
    protected HashingService hashingService;
}
