package maven.blackjack2.standing_simulator;
import maven.blackjack2.Hand;

public interface StandingSimulator {
	public static final double WIN = 1.0;
	public static final double LOSS = -1.0;
	public static final double PUSH = 0.0;
	public static final double BLACKJACK = 1.5;
	
	public static final Double WIND = 1.0;
	public static final Double LOSSD = -1.0;
	public static final Double PUSHD = 0.0;
	public static final Double BLACKJACKD = 1.5;

	public double expected_return();
	public Double Expected_return();

	default long hash_key(Hand player, Hand dealer) {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
	
	default Long Hash_key(Hand player, Hand dealer) {
		return player.hashCode() | (((long) dealer.hashCode()) << 31);
	}
}
