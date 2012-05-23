package session.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import session.Game;
import constants.Constants;
import java.util.*;

import session.Gamer;

public class AllSessions {
    
	private Collection<Gamer> sessions = new ArrayList<Gamer>();
        
        private Collection<Game> games = new ArrayList<Game>();
	
	public synchronized ArrayList<String> addGamer(Gamer g) {
            
		this.sessions.add(g);
                
                if(games.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    Game game = new Game(uuid);
                    this.games.add(game);
                }
                
                Game playersGame = null;
                
                for(Game gz : games) {
                    if(gz.getPlayersCount() < Constants.CardsPerPlayer) {
                        gz.addGamer(g);
                        playersGame = gz;
                        break;
                    }
                }
                
                if(playersGame == null) {
                    UUID uuid = UUID.randomUUID();
                    playersGame = new Game(uuid);
                    this.games.add(playersGame);
                    playersGame.addGamer(g);
                }

                return this.otherGamersNames(g, playersGame);
	}
        
        public synchronized ArrayList<String> checkOtherGamers(Gamer g) {
            
            Game playersGame = null;
            
            for(Game gm : this.games) {
                if(gm.containsGamer(g)) {
                    playersGame = gm;
                    break;
                }
            }
            
            if(playersGame != null) {
                return this.otherGamersNames(g, playersGame);
            }
            else {
                return new ArrayList<String>();
            }
        }
	
        private ArrayList<String> otherGamersNames(Gamer thisGamer, Game g) {
            
            ArrayList<HashMap<String, Object>> gamers = g.getGamers();
            
            ArrayList<String> result = new ArrayList<String>();
            
            for(HashMap h : gamers) {
                Gamer gamer = (Gamer) h.get("player");
                
                if(gamer.equals(thisGamer) == false) {
                    result.add(gamer.getClientName());
                }
            }
            return result;
        }
        
        
	public Iterator<Gamer> iterator(){
		return sessions.iterator();
	}
        
        private synchronized void addGame(Game g) {
            this.games.add(g);
        }
        
        public synchronized void removePlayer(Gamer g) {
            
            this.sessions.remove(g);
            
            for(Game gm : this.games) {
                if(gm.removeGamer(g) == true) {
                    break;
                }
            }
        }
        
        public synchronized boolean gamerStartsGame(Gamer g) {
            
            for(Game game : this.games) {
                if(game.containsGamer(g)) {
                    return game.gamerStartedGame(g);
                }
            }
            
            return false;
        }
}