package session;

import java.io.*;
import java.net.Socket;
import javax.swing.JApplet;

import session.active.AllSessions;
public class Gamer extends Thread {
	
	private Socket socket;
	private AllSessions sessions;
	private BufferedReader netIn;
	private PrintWriter netOut;
        private JApplet mainApplet;

	public Gamer(Socket sock, AllSessions otherGamers) throws IOException {
		super();
		this.socket = sock;
		this.sessions = otherGamers;
		this.netIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.netOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
		start();
	}
	
        @Override
	public void run() 
	{
            try {
                while( true ) {
                    
                }
            }
            finally {
                
            }
	}
}