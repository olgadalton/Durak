package session;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import javax.swing.JApplet;

import session.active.AllSessions;
import utils.JSONUtil;
public class Gamer extends Thread {
	
	private Socket socket;
	private AllSessions sessions;
	private BufferedReader netIn;
	private PrintWriter netOut;
        private JApplet mainApplet;
        private String clientName;

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
                        
                        System.out.println("obj" + obj);
                        
                        if(obj.get("action").equals("regAndStartGame")) {
                            System.out.println("action " + obj.get("action"));
                            this.clientName = (String) obj.get("client");
                            System.out.println("Client name " + this.clientName);
                            boolean canPlay = this.sessions.addGamer(this);
                            System.out.println("can play " + canPlay);
                            HashMap<String, Object> responseData = new HashMap<>();
                            responseData.put("status", "wait");
                            responseData.put("enough", canPlay);
                            String response = this.composeResponse(responseData);
                            System.out.println("response " + response);
                            this.netOut.println(response);
                        }
                    } 
                    catch (Exception ex) {}
                    
                    System.out.println(message);
                    
                }
            }
            
            catch(IOException ex) {}
            
            finally {
                
                try {
                    this.socket.close();
                } 
                catch (IOException ex) {}
            }
	}
        
        private String composeResponse(Object message) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("server", message);
            String json = JSONUtil.jsonEncode(result);
            return json;
        }
}