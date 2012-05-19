package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import session.Gamer;
import session.active.AllSessions;

public class Server {
	
	private static final Integer serverPort = 8080;
	
	public static void main(String[] args) throws IOException
	{
		AllSessions allActiveSessions = new AllSessions();
		
		ServerSocket serv = new ServerSocket(serverPort);
		
		System.out.println("Server started on port " + serverPort);
		
		try {
			while(true){
				
				Socket sock = serv.accept();
				
				try
				{
					new Gamer(sock, allActiveSessions);
				}
				catch(IOException e)
				{
					sock.close();
				}
			}
		}
		catch(Exception e){}
		finally {
			serv.close();
		}
	}
}