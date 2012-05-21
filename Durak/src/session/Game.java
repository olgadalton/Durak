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
    
    public boolean removeGamer(Gamer g) {
        
        HashMap<String, Object> hashMap = null;
        
        for(HashMap<String, Object> hm: this.gamers) {
            if(hm.get("player").equals(g)){
                hashMap = hm;
            }
        }
        
        if(hashMap != null) {
            this.gamers.remove(hashMap);
            return true;
        }
    
        return false;
    }
    
    public boolean gamerStartedGame(Gamer gamer) {
        
        for(HashMap<String, Object> hm : this.gamers) {
            if(hm.get("player").equals(gamer)) {
                hm.put("activated", true);
                break;
            }
        }
        
        boolean allActivated = true;
        
        for(HashMap<String, Object> hm : this.gamers) {
            if(hm.get("activated").equals(false)) {
                allActivated = false;
            }
        }
        
        return allActivated;
    }
            
}
