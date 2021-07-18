package ZeldaGame;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Rohith Perla
 * Zelda class - Starts the application, which holds the game map and the gui that controls it
 *
 */

public class Zelda extends JFrame{
	

	public Zelda() throws IOException {
		initUI();
	}
	
	private void initUI() throws IOException { //Initializes the board and the gui for the game
		JPanel game = new JPanel();
		add(game);
		game.setLayout(new BoxLayout(game, BoxLayout.PAGE_AXIS));
		ZeldaBoard board = new ZeldaBoard();
		game.add(board);
		game.add(new Gui(board));
		setTitle ("Link's Final Battle");
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo (null);
        setVisible (true); 
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater (new Runnable() {//Initializes a new Runnable after all pending events are processed
        	public void run() {
        		Zelda ex;
				try {
					ex = new Zelda();
	        		ex.setVisible (true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

        	}
        });
	}

}
