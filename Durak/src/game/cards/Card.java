package game.cards;

public interface Card {
	public boolean killableWith(Card card);
	public Suit getSuit();
	public int getValue();
}

