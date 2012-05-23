package game.cards;

public class Trump implements Card {

	private Suit suit;
	private int value;

	public Trump(Suit suit, int val) {
		this.suit = suit;
		this.value = val;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public boolean killableWith(Card card) {
		// TODO Auto-generated method stub
		return false;
	}
}
