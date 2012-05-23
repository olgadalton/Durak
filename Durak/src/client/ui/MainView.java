/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import client.GamerClient;
import client.ui.LoginForm;
import client.ui.WaitForPlayersView;

/**
 *
 * @author Olga
 */
public class MainView extends JFrame {
    
    private GamerClient clientHandler;
    private LoginForm loginForm;
    private WaitForPlayersView waitView;
    private GameView gameView;
    
    public MainView() {
        super();
        setup();
        manageClose();
    }
    
    private void setup() {
        this.clientHandler = new GamerClient(this);
        loginForm = new LoginForm(clientHandler, this);
        add(loginForm);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void manageClose(){
        
        this.addWindowListener(new WindowAdapter() {
            
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        clientHandler.closeSocket();
                    } 
                    catch (IOException ex) {}
                }
            });
    }
    
    public void showWaitingView() {
        System.out.println("Show e´wait view");
        waitView = new WaitForPlayersView(clientHandler);
        remove(loginForm);
        add(waitView);
        setVisible(true);
    }
    
    public void updateWaitingView(ArrayList<String> players) {
        this.waitView.updateListView(players);
        this.waitView.changeStartButtonState(!players.isEmpty());
    }
    
    public static void main(String[] args) {
        new MainView();
    }
    
    public void gameStartedOtherNotReady() {
        waitView.showWaitForOthersOnButton();
    }
    
    public void gameStartedOtherReady() {
        gameView = new GameView();
        remove(waitView);
        add(gameView);
        setVisible(true);
    }
    
}
