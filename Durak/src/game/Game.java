/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.cards.Card;
import game.cards.NotTrump;
import game.cards.Suit;
import game.cards.Trump;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import serverPart.Gamer;
import constants.Constants;

/**
 * 
 * @author Olga
 */
public class Game {

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> deck;
	private Suit trump;
	private Card trumpCard;
	private Player attacker;
	private Player defender;
	private int attackerNr;
	private int defenderNr;

	public Game() {
		Random rand = new Random();
		int suitRandom = rand.nextInt(4);
		this.trump = Suit.values()[suitRandom];
		generateDeck(suitRandom);
	}

	public synchronized void addGamer(Gamer gamer) {
		Player player = new Player(gamer);
		this.players.add(player);
	}

	public boolean containsGamer(Gamer g) {

		for (Player pl : this.players) {
			if (pl.getGamer().equals(g)) {
				return true;
			}
		}
		return false;
	}

	public boolean gamerStartedGame(Gamer gamer) {

		for (Player pl : this.players) {

			if (pl.getGamer().equals(gamer)) {

				pl.activate();

				ArrayList<Card> playerCards = new ArrayList<Card>();

				for (int i = 0; i < Constants.CardsPerPlayer; i++) {
					playerCards.add(this.deck.get(0));
					this.deck.remove(0);
				}

				pl.giveCards(playerCards);
				break;
			}
		}

		boolean allActivated = true;

		for (Player pl : this.players) {
			if (pl.isActivated() == false) {
				allActivated = false;
			}
		}

		return allActivated;
	}

	private void generateDeck(int suitRandom) {

		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = Constants.smallestValue; j < Constants.biggestValue; j++) {
				if (j == suitRandom) {
					Trump trump = new Trump(Suit.values()[j], i);
					this.deck.add(trump);
				} else {
					NotTrump card = new NotTrump(Suit.values()[j], i);
					this.deck.add(card);
				}
			}
		}

		Collections.shuffle(this.deck);

		this.trumpCard = this.deck.get(Constants.CardsCount - 1);

	}

	public Player getAttacker() {
		return this.attacker;
	}

	public int getAttackerNr() {
		return this.attackerNr;
	}

	public Player getDefender() {
		return this.defender;
	}
	public int getDefenderNr() {
		return this.defenderNr;
	}
	public ArrayList<String> getGamers(Gamer askingGamer) {

		ArrayList<String> names = new ArrayList<String>();

		for (Player pl : this.players) {

			if (!pl.getGamer().equals(askingGamer)) {
				names.add(pl.getGamer().getClientName());
			}
		}

		return names;
	}
	public int getPlayersCount() {
		return this.players.size();
	}
	public int getPlayerWithSmallestCard() {
		for (int i = Constants.smallestValue; i < Constants.biggestValue; i++) {
			for (Player player : this.players) {
				if (player.hasCard(i)) {
					return this.players.indexOf(player);
				}
			}
		}
		return -1;
	}
	public boolean removeGamer(Gamer g) {

		Player player = null;

		for (Player pl : this.players) {
			if (pl.getGamer().equals(g)) {
				player = pl;
			}
		}

		if (player != null) {
			this.players.remove(player);
			return true;
		}

		return false;
	}
	private void setActivePlayers() {
		this.attackerNr++;
		this.attackerNr %= this.players.size();

		this.attacker = this.players.get(this.attackerNr);

		this.defenderNr = (this.attackerNr + 1) % this.players.size();
		this.defender = this.players.get(this.defenderNr);
	}
}
