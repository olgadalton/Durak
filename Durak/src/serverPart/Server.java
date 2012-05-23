package serverPart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class Server extends JFrame implements ActionListener {
	
        SessionsHandler sessions;
        private static final Integer serverPort = 8080;
        private static ServerSocket server;
        private BufferedReader input;
        private PrintWriter output;
        private Container c ;
        private JTextArea display ;
        private JButton exit;
        private JPanel buttonPanel ;
        
        public Server() throws IOException{
            super();
            setup();
            runServer();
        }
        
        private void setup(){
            c = getContentPane();
            exit = new JButton( "Exit" );
            exit.setBackground( Color.red ) ;
            exit.setForeground( Color.white ) ;
            buttonPanel = new JPanel() ;
            buttonPanel.add( exit ) ;
            c.add( buttonPanel , BorderLayout.SOUTH) ;

            exit.addActionListener( this );

            display = new JTextArea();
            c.add( new JScrollPane( display ),
                    BorderLayout.CENTER );

            setSize( 400, 400 );
            setLocation( 10, 20 ) ;
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            this.addWindowListener(new WindowAdapter() {

                //
                // Invoked when a window has been closed.
                //
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("Window Close Event");
                    
                    try {
                        server.close();
                    } 
                    catch (IOException ex) {}
                }
            });
            
            setVisible(true);
        }
        
        private void runServer() throws IOException {
            server = new ServerSocket(serverPort);
            display.setText("Server waiting for client on port " +
			       server.getLocalPort() + "\n");
            
                                
            sessions = new SessionsHandler();
            
            try {
                while(true) {
                    
                    Socket sock = server.accept();
                    
                    display.append("New client connection established " + 
                                                  sock.toString() + "\n");
                    
                    Gamer gamer = new Gamer(sock, sessions);
                }
            }
            catch(IOException e){}
            finally {
                server.close();
            }
        }
	
	public static void main(String[] args) throws IOException
	{
                Server serv = new Server();
	}
        
        private void sysExit() throws IOException{
            server.close();
            System.exit(1);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                this.sysExit();
            } 
            catch (IOException ex) {}
        }
}