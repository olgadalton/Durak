/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Olga
 */
public class Game {
    private ArrayList<HashMap<String, Object>> gamers = new ArrayList<>();
    private UUID myID;
    
    public Game(UUID uID) {
        this.myID = uID;
    }
    
    public int getPlayersCount() {
        return this.gamers.size();
    }
    
    public synchronized void addGamer(Gamer gamer) {
        HashMap<String, Object> player = new HashMap<>();
        player.put("activated", false);
        player.put("player", gamer);
        System.out.println(player);
        gamers.add(player);
    }
    
    public ArrayList<HashMap<String, Object>> getGamers(){
        return this.gamers;
    }
    
    public boolean containsGamer(Gamer g) {
        
        for(HashMap<String, Object> hm: this.gamers) {
            if(hm.get("player").equals(g)){
                return true;
            }
        }
        return false;
    }
            
}
