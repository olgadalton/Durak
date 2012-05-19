package session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import session.active.AllSessions;
public class Gamer extends Thread {
	
	private Socket socket;
	private AllSessions sessions;
	private BufferedReader netIn;
	private PrintWriter netOut;

	public Gamer(Socket sock, AllSessions otherGamers) throws IOException {
		super();
		this.socket = sock;
		this.sessions = otherGamers;
		this.netIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.netOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
		start();
	}
	
	public void run() 
	{
		while(true)
		{
			netOut.println("Server already has an active player! Please try again later!");
			
			try 
			{
				// wait and exit
				sleep(50000000);
				this.socket.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}