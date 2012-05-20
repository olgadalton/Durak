/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import javax.swing.JFrame;
import session.GamerClient;
import ui.enter.LoginForm;

/**
 *
 * @author Olga
 */
public class MainView extends JFrame {
    
    private GamerClient clientHandler;
    
    public MainView() {
        super();
        this.clientHandler = new GamerClient();
        LoginForm loginForm = new LoginForm(clientHandler);
        add(loginForm);
        setSize(400, 400);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainView();
    }
    
}
