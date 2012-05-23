package game;

import game.cards.Card;

import java.util.ArrayList;

import serverPart.Gamer;

public class Player {

	private boolean activated;
	private Gamer gamer;
	private ArrayList<Card> myCards;

	public Player(Gamer gamer) {
		super();
		this.gamer = gamer;
		this.activated = false;
	}

	public void activate() {
		this.activated = true;
	}

	public Gamer getGamer() {
		return this.gamer;
	}

	protected void giveCards(ArrayList<Card> cards) {
		this.myCards = cards;
	}

	protected boolean hasCard(int value) {
		for (Card card : this.myCards) {
			if (card.getValue() == value) {
				return true;
			}
		}
		return false;
	}

	public boolean isActivated() {
		return this.activated;
	}

}
