package session;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JApplet;

import session.active.AllSessions;
import utils.JSONUtil;
public class Gamer extends Thread {
	
	private Socket socket;
	private AllSessions sessions;
	private BufferedReader netIn;
	private PrintWriter netOut;
        private String clientName;

        public String getClientName() {
            return clientName;
        }

	public Gamer(Socket sock, AllSessions otherGamers) throws IOException {
		super();
		this.socket = sock;
		this.sessions = otherGamers;
                
		this.netIn = new BufferedReader(
                        new InputStreamReader(this.socket.getInputStream()));
                
		this.netOut = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(
                        this.socket.getOutputStream())), true);
                
		start();
	}
	
        @Override
	public void run() 
	{
            try {
                while( true ) {
                    
                    String message = this.netIn.readLine();
                    
                    try {
                        HashMap<String, Object> obj = 
                                (HashMap<String, Object>) 
                                JSONUtil.decodeJson(message);
                        
                        if(obj.get("action").equals("regAndStartGame") ||
                                obj.get("action").equals("check")) {
                            
                            ArrayList<String> players = null;
                            
                            if(obj.get("action").equals("regAndStartGame")) {
                            
                                this.clientName = (String) obj.get("client");
                                players = this.sessions.addGamer(this);
                                
                            }
                            else if(obj.get("action").equals("check")) {
                            
                                players = this.sessions.checkOtherGamers(this);
                            }
                            
                            boolean enough = false;
                            
                            if(players.isEmpty() == false) {
                                enough = true;
                            }
                            
                            HashMap<String, Object> responseData = 
                                    new HashMap<String, Object>();
                            
                            responseData.put("status", "wait");
                            responseData.put("enough", enough);
                            responseData.put("players", players);
                            String response = this.composeResponse(
                                                            responseData);
                            
                            this.netOut.println(response);
                        }
                        else if(obj.get("action").equals("justStartGame")) {
                            
                            boolean result = this.sessions.gamerStartsGame(this);
                            
                            HashMap<String, Object> responseData = 
                                    new HashMap<String, Object>();
                            
                            responseData.put("status", "startStatus");
                            responseData.put("otherStarted", result);
                            
                            String response = this.composeResponse(responseData);
                            
                            this.netOut.println(response);
                        }
                    } 
                    catch (Exception ex) {}
                    
                }
            }
            
            catch(IOException ex) {
                this.sessions.removePlayer(this);
            }
            
            finally {
                
                try {
                    this.socket.close();
                } 
                catch (IOException ex) {}
            }
	}
        
        private String composeResponse(Object message) {
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("server", message);
            String json = JSONUtil.jsonEncode(result);
            return json;
        }
}