package ZeldaGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * 
 * @author Rohith Perla
 * Gui class - Holds all the controls of the game as well as a help area
 *
 */

@SuppressWarnings("serial")
public class Gui extends JPanel {
	private Link link;

	public Gui(ZeldaBoard board) {
		link = board.getLink();
		setFocusable(true);
		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel controls = new JPanel(new FlowLayout());
		JPanel commands = new JPanel(new GridLayout(3, 3));
		JPanel starter = new JPanel();
		starter.setLayout(new BorderLayout());
		JTextArea help = new JTextArea();
		help.setEditable(false);
		help.setText("Controls:" + "\n" + "Up, down, left, right buttons and keys are to move" + "\n"
				+ "Attack button and space bar make Link stop and attack in the direction he's facing" + "\n"
				+ "Exit button and escape key stops and exits the game" + "\n" + "\n"
				+ "Objective: Kill all monsters" + "\n"
				+ "Once you kill all the smaller monsters, the cage around Ganon will open" + "\n"
				+ "You have to land multiple hits to defeat him. Once you do, you win the game. Good luck!" + "\n"
				+ "HINT: Try to stay away from the walls when you kill the last small monster. Things will get heated up after you do.");
		controls.setBackground(Color.BLACK);
		commands.setBackground(Color.BLACK);

		//The following code for the JButtons up, down, left, right, attack, start, and exit make buttons on screen in the Gui. The code also includes support for keyboard commands 
		JButton up = new JButton("\u25b2");
		up.setFocusable(false);
		up.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "Up");
		up.getActionMap().put("Up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 0;
					link.req_dy = -1;
				}
			}
		});
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 0;
					link.req_dy = -1;
				}
			}
		});

		JButton down = new JButton("\u25bc");
		down.setFocusable(false);
		down.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "Down");
		down.getActionMap().put("Down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 0;
					link.req_dy = 1;
				}
			}
		});
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 0;
					link.req_dy = 1;
				}
			}
		});

		JButton left = new JButton("\u25c0");
		left.setFocusable(false);
		left.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "Left");
		left.getActionMap().put("Left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = -1;
					link.req_dy = 0;
				}
			}
		});
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = -1;
					link.req_dy = 0;
				}
			}
		});

		JButton right = new JButton("\u25b6");
		right.setFocusable(false);
		right.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "Right");
		right.getActionMap().put("Right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 1;
					link.req_dy = 0;
				}
			}
		});
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setSpeed(3);
					link.setBurned(false);
					link.req_dx = 1;
					link.req_dy = 0;
				}
			}
		});

		JButton attack = new JButton("Attack");
		attack.setFocusable(false);
		attack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "Space");
		attack.getActionMap().put("Space", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setBurned(false);
					link.setSpeed(0);
				}
			}
		});
		attack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.isInGame() == true) {
					link.setBurned(false);
					link.setSpeed(0);
				}
			}
		});

		JButton start = new JButton("Start");
		start.setFocusable(false);
		start.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "Enter");
		start.getActionMap().put("Enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
					board.setInGame(true);
			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.setInGame(true);
			}
		});

		JButton restart = new JButton("Exit");
		restart.setFocusable(false);
		restart.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "Escape");
		restart.getActionMap().put("Escape", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (board.getTimer().isRunning())
					System.exit(1);
			}
		});
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.getTimer().isRunning())
					System.exit(1);
			}
		});
		
		//Adds all the buttons defined  earlier to the Gui, which acts as a JPanel
		add(controls);//Adds the controls JPanel
		controls.add(commands);//Adds the commands and starter JPanels, which hold the directional controls for Link, and the start and exit buttons respectively
		controls.add(starter);
		commands.add(new JLabel());//Empty JLabels to make the buttons appear in a d-pad layout
		commands.add(up);
		commands.add(new JLabel());
		commands.add(left);
		commands.add(attack);
		commands.add(right);
		commands.add(new JLabel());
		commands.add(down);
		commands.add(new JLabel());
		starter.add(start, BorderLayout.PAGE_START);
		starter.add(restart, BorderLayout.PAGE_END);
		add(help);

	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
}
