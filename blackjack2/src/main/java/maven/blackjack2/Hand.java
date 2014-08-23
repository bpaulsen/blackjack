package maven.blackjack2;

import java.util.Arrays;

public class Hand implements Cloneable {
	private int hard_count = 0;
	private int card_count = 0;
	private int[] cards = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private final static int[] bitshifts = { 0, 0, 5, 9, 13, 16, 19, 22, 25, 27, 29 };
	private int hash_code = 0;
	
	public Hand() {
	}
	
	public Hand clone() {
		try {
			Hand clone = new Hand();
			for (int i=10; i > 0; --i) {
				clone.cards[i] = cards[i];
			}
			clone.hard_count = hard_count;
			clone.card_count = card_count;
			clone.hash_code = hash_code;
			return clone;			
		}
		catch(Exception e){ 
	        return null; 
	    }

	}
	
	@Override
	public int hashCode() {
		return hash_code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hand other = (Hand) obj;
		if (!Arrays.equals(cards, other.cards))
			return false;
		return true;
	}

	public void add_card(int card_number) throws IllegalArgumentException {
		if (card_number < 1 || card_number > 10) {
			throw new IllegalArgumentException("Card number must be between 1 and 10");
		}
		card_count++;
		hard_count += card_number;	
		cards[card_number]++;
		hash_code += 1 << bitshifts[card_number];
	}
	
	public void remove_card(int card_number) throws IllegalArgumentException {
		if (card_number < 1 || card_number > 10) {
			throw new IllegalArgumentException("Card number must be between 1 and 10");
		}
		card_count--;
		hard_count -= card_number;
		cards[card_number]--;
		hash_code -= 1 << bitshifts[card_number];
	}
	
	public int count() {
		if (hard_count > 11 || cards[1] == 0 ) {
			return hard_count;
		}
		else {
			return hard_count + 10;
		}
	}
	
	public int hard_count() {
		return hard_count;
	}
	
	public int card_count() {
		return card_count;
	}
	
	public boolean blackjack() {
		return card_count == 2 && hard_count == 11 && cards[1] == 1 && cards[10] == 1;
	}
}
