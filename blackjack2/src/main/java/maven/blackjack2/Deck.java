package maven.blackjack2;

import maven.blackjack2.Hand;
import java.util.Arrays;

public class Deck implements Cloneable {
	private int[] cards = new int[11];
	private int count;
	
	private Deck() {
	}
	
	public Deck clone() {
		try {
			Deck clone = new Deck();
			for (int i=10; i != 0; --i) {
				clone.cards[i] = cards[i];
			}
			clone.count = count;
			return clone;
		}
		catch(Exception e){ 
	        return null; 
	    }

	}
	
	public Deck(int number_of_decks) throws IllegalArgumentException {
		if (number_of_decks < 1) {
			throw new IllegalArgumentException("Number of decks must be greater than 0");
		}
		for (int i=1; i<=10; i++) {
			cards[i] = number_of_decks * (i == 10 ? 16 : 4);
		}
		count = number_of_decks * 52;
	}
	
	public int count() {
		return this.count;
	}
	
	public int count(int card_number) throws IllegalArgumentException {
		if (card_number < 1 || card_number > 10) {
			throw new IllegalArgumentException("Card number must be between 1 and 10");
		}
		return this.cards[card_number];		
	}
	
	public void deal_card(Hand hand, int card_number) throws IllegalArgumentException, IllegalStateException {
		if (card_number < 1 || card_number > 10) {
			throw new IllegalArgumentException("Card number must be between 1 and 10");
		}
		if (this.cards[card_number] == 0) {
			throw new IllegalStateException("No cards left of that number");
		}
		this.count--;
		this.cards[card_number]--;
		hand.add_card(card_number);
	}
	
	public void undeal_card(Hand hand, int card_number) throws IllegalArgumentException {
		if (card_number < 1 || card_number > 10) {
			throw new IllegalArgumentException("Card number must be between 1 and 10");
		}
		this.count++;
		this.cards[card_number]++;
		hand.remove_card(card_number);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cards);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deck other = (Deck) obj;
		if (!Arrays.equals(cards, other.cards))
			return false;
		return true;
	}
}

