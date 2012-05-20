/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import java.util.ArrayList;
import javax.swing.JFrame;
import session.GamerClient;
import ui.enter.LoginForm;
import ui.enter.WaitForPlayersView;

/**
 *
 * @author Olga
 */
public class MainView extends JFrame {
    
    private GamerClient clientHandler;
    private LoginForm loginForm;
    private WaitForPlayersView waitView;
    
    public MainView() {
        super();
        setup();
    }
    
    public void setup() {
        this.clientHandler = new GamerClient(this);
        loginForm = new LoginForm(clientHandler, this);
        add(loginForm);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
    }
    
    public static void main(String[] args) {
        new MainView();
    }
    
}
