package session.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import session.Game;
import constants.Constants;

import session.Gamer;

public class AllSessions {
    
	private Collection<Gamer> sessions = new ArrayList<Gamer>();
        
        private Collection<Game> games = new ArrayList<Game>();
	
	public synchronized boolean addGamer(Gamer g) {
            
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
                
                if(playersGame.getPlayersCount() >= Constants.MinPlayersCount) {
                    return true;
                }
                else {
                    return false;
                }
	}
	
	public Iterator<Gamer> iterator(){
		return sessions.iterator();
	}
        
        public synchronized void addGame(Game g) {
            this.games.add(g);
        }
}