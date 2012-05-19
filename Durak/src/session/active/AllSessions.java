package session.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import session.Gamer;

public class AllSessions {
	private Collection<Gamer> sessions = new ArrayList<Gamer>();
	
	public synchronized void addGamer(Gamer g) {
		this.sessions.add(g);
	}
	
	public Iterator<Gamer> iterator(){
		return sessions.iterator();
	}
}