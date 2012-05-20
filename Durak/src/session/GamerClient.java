/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import ui.main.MainView;
import utils.JSONUtil;

/**
 *
 * @author Olga
 */
public class GamerClient extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String clientName;
    private final static Integer PORT = 8080;
    private final static String HOST = "localhost";
    private MainView delegate;
    
    public GamerClient(MainView _delegate){
        
        this.delegate = _delegate;
        
        try {
            this.socket = new Socket(HOST, PORT);
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            
            output = new PrintWriter(socket.getOutputStream(),true);
        } 
        
        catch (UnknownHostException ex) {} 
        catch (IOException ex) {}
    }
    
    public void registerForGame(String name){
        
        this.clientName = name;
        
        this.output.println(
                this.doServerRequest("regAndStartGame"));
        
        start();
    }
    
    @Override
    public void run() {
            try {
                while( true ) {
                    
                    String message = this.input.readLine();
                    
                    try {
                        HashMap<String, Object> response =
                                (HashMap<String, Object>)
                                    JSONUtil.decodeJson(message);
                        
                        HashMap<String, Object> responseData =
                                (HashMap<String, Object>)
                                    response.get("server");
                        
                        if(responseData.get("status").equals("wait")) {
                            
                            this.delegate.updateWaitingView(
                            (ArrayList<String>) responseData.get("players"));
                                    
                                    this.output.println(
                                            this.doServerRequest("check"));
                        }
                    }
                    catch(Exception ex) {}
                    
                }
            }
            catch(IOException ex) {}
            finally {
                
            }
	}
    
        private String doServerRequest(Object message) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("client", this.clientName);
            result.put("action", message);
            String json = JSONUtil.jsonEncode(result);
            return json;
    }
}
