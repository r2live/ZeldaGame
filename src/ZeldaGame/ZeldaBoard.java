package ZeldaGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Rohith Perla
 * ZeldaBoard class - Makes the game happen
 *
 */

@SuppressWarnings("serial")
public class ZeldaBoard extends JPanel {
	private final static int N_BLOCKS = 20;//Number of blocks on the board - Makes a 20 x 20 map

	private final static int BLOCK_SIZE = 24; //Dimensions of a block, as well as other variables related to size, for situational use
	private final static int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
	private final static int BORDER_SIZE = 25;

	private final static int LEFT_CORNER = 1; //Defines the types of terrain on the board - for use in the drawMap() method
	private final static int TOP_CORNER = 2;
	private final static int RIGHT_CORNER = 4;
	private final static int BOTTOM_CORNER = 8;
	private final static int EMPTY_SPACE = 16;

	private Link link; //Variables for instances of the characters in the game
	private Knight knight;
	private Moblin moblin;
	private Wallmaster wallmaster;
	private Stalfos stalfos;
	private Bokoblin bokoblin;
	private Ganon ganon;

	private Triforce triforce;

	private boolean inGame = false; //Booleans to define the state of the game and makes different events happen if different booleans are true
	private boolean hurt = false;
	private boolean init = true;
	private boolean end = false;
	private boolean victory = false;

	private int numMonsters = 5;
	
	private int invulnerability = 0; //"Timer" for Link's invulnerability, preventing him from being damaged again if he is damaged by a monster

	private int hearts;

	private Timer timer;
	
	private short[][] screenData; //Array for the terrain data, so that the characters can properly collide with walls if there's a corner

	private Fire[][] fire = new Fire[4][19];//Array for all the Fire objects that spawn when all the small monsters are defeated

	private final short levelData[][] = { { 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6 },
			{ 1, 0, 8, 8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 8, 0, 4 },
			{ 1, 4, 3, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 6, 1, 4 },
			{ 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 0, 8, 8, 8, 8, 0, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 4, 3, 2, 2, 6, 1, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 4, 1, 0, 0, 4, 1, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 4, 1, 0, 0, 4, 1, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 0, 0, 0, 0, 4, 1, 4, 9, 8, 8, 12, 1, 4, 1, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 4, 1, 0, 2, 2, 2, 2, 0, 4, 1, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 4, 1, 0, 8, 8, 8, 8, 0, 4, 1, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 4, 1, 0, 2, 2, 2, 2, 0, 4, 1, 0, 0, 0, 0, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 0, 0, 0, 0, 0, 0, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 0, 4, 1, 4, 1, 0, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 0, 4, 1, 4, 1, 0, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 4, 1, 4, 1, 0, 4, 1, 4, 1, 0, 4, 1, 4, 1, 4, 1, 4 },
			{ 1, 4, 1, 0, 0, 0, 0, 0, 4, 1, 4, 1, 0, 0, 0, 0, 0, 4, 1, 4 },
			{ 1, 4, 9, 8, 8, 8, 8, 0, 4, 1, 4, 1, 0, 8, 8, 8, 8, 12, 1, 4 },
			{ 1, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 4 },
			{ 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12 } };

	private final short bossData[][] = { { 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
			{ 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12 } };

	public ZeldaBoard() throws IOException {//Constructor for the class
		setMinimumSize(new Dimension(SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));
		setPreferredSize(new Dimension(SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));

		initVariables();
		initBoard();
		initGame();
	}

	private void initVariables() {//Initializes the screenData and starts a timer
		screenData = new short[N_BLOCKS][N_BLOCKS];

		timer = new Timer(40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();

	}

	private void initBoard() {//Initializes the board
		setFocusable(true);
		setDoubleBuffered(true);

	}

	public void initGame() throws IOException {//Starts the game

		hearts = 10;

		initLevel();

	}

	private void initLevel() throws IOException {//Makes the screenData the data for the first level

		for (int r = 0; r < N_BLOCKS; r++)
			for (int c = 0; c < N_BLOCKS; c++)
				screenData[r][c] = levelData[r][c];

		continueLevel();

	}

	private void continueLevel() throws IOException {//Spawns the monsters and Link at the start of the game and makes Link not hurt anymore if he got damaged

		if (init == true) {
			knight = new Knight(screenData, 10, 4, BLOCK_SIZE);
			moblin = new Moblin(screenData, 2, 2, BLOCK_SIZE);
			wallmaster = new Wallmaster(screenData, 17, 2, BLOCK_SIZE);
			stalfos = new Stalfos(screenData, 2, 17, BLOCK_SIZE);
			bokoblin = new Bokoblin(screenData, 17, 17, BLOCK_SIZE);
			ganon = new Ganon(screenData, 9, 6, BLOCK_SIZE);
			init = false;
		}

		hurt = false;
		if (hearts == 10)
			link = new Link(screenData, 7 * BLOCK_SIZE, 11 * BLOCK_SIZE, 0, 0, 1, 0);
	}

	private void playGame(Graphics2D g2d) throws IOException {//Checks if Link is hurt, and moves him, the monsters, and checks the map to see if certain conditions are fulfilled
		if (hurt) {
			hurt();
		} else {
			moveLink(g2d);
			moveMonsters(g2d);
			checkMap();
		}

	}

	private void showIntroScreen(Graphics2D g2d) { //Shows the title screen before the game starts
		g2d.setColor(new Color(0, 32, 48));
		g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
		g2d.setColor(Color.white);
		g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

		String s = "Link's Final Battle";
		String s2 = "Press Enter or Start to begin";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2 - 10);
		g2d.drawString(s2, (SCREEN_SIZE - metr.stringWidth(s2))/2, SCREEN_SIZE/2 + 10);

	}

	private void drawHearts(Graphics2D g2d) { //Draws the remaining hearts at the bottom and also has a numerical counter for the hearts remaining
		String h = "Hearts: " + hearts;
		Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		g2d.setFont(smallFont);
		g2d.setColor(new Color(250, 128, 114));
		g2d.drawString(h, SCREEN_SIZE/2 + 170, SCREEN_SIZE + 12);
		for (int i = 0; i < hearts; i++)
			g2d.drawImage(link.getHeart(), i * 28 + 8, SCREEN_SIZE + 1, this);
	}

	private void checkMap() {//Checks the map to see if certain conditions are fulfilled
		if(hearts == 0) {//If Link's hearts reach 0, it's game over
			System.out.println("Game Over");
			System.exit(1);
		}
		if (numMonsters == 0) {//Checks to see if all the small monsters have been killed and moves the game into the second phase if they are
			screenData = bossData;
			link.setScreenData(screenData);
			ganon.setScreenData(screenData);
			for (int i = 0; i < 19; i++) {
				fire[0][i] = new Fire(screenData, i, 0, BLOCK_SIZE);
			}
			for (int i = 0; i < 19; i++) {
				fire[1][i] = new Fire(screenData, 19, i, BLOCK_SIZE);
			}
			for (int i = 0; i < 19; i++) {
				fire[2][i] = new Fire(screenData, i + 1, 19, BLOCK_SIZE);
			}
			for (int i = 0; i < 19; i++) {
				fire[3][i] = new Fire(screenData, 0, i + 1, BLOCK_SIZE);
			}
			ganon.setSpeed(2);
			end = true;
		}
		if (ganon.isAlive() == false) {//Checks to see if Ganon is dead and spawns a path to the end of the game if he is
			triforce = new Triforce(screenData, 10, 6, BLOCK_SIZE);
			end = false;
			victory = true;
		}

	}

	private void hurt() throws IOException {//If Link is hurt, then his hearts decrease, he is made invulnerable for a short amount of time, and the game continues
		hearts--;
		hurt = false;
		link.setVulnerable(false);
		continueLevel();
	}

	private void moveLink(Graphics2D g2d) {//Moves and draws Link, and also includes some code ot make Link invulnerable for a short amount of time if he got damaged
		if (link.isVulnerable() == false) {
			invulnerability += 1;
			if (invulnerability >= 20) {
				link.setVulnerable(true);
				invulnerability = 0;
			}
		}
		link.move();
		link.draw(g2d);

	}

	private void moveMonsters(Graphics2D g2d) {//Moves the monsters and checks to see if any of them touched Link or his sword

		knight.move();
		knight.draw(g2d);
		if (knight.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (knight.swordcontains(link.getEdge(), link.getSpeed())) {
			knight.setAlive(false);
			numMonsters--;
		}

		moblin.move();
		moblin.draw(g2d);
		if (moblin.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (moblin.swordcontains(link.getEdge(), link.getSpeed())) {
			moblin.setAlive(false);
			numMonsters--;
		}

		wallmaster.move();
		wallmaster.draw(g2d);
		if (wallmaster.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (wallmaster.swordcontains(link.getEdge(), link.getSpeed())) {
			wallmaster.setAlive(false);
			numMonsters--;
		}

		stalfos.move();
		stalfos.draw(g2d);
		if (stalfos.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (stalfos.swordcontains(link.getEdge(), link.getSpeed())) {
			stalfos.setAlive(false);
			numMonsters--;
		}

		bokoblin.move();
		bokoblin.draw(g2d);
		if (bokoblin.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (bokoblin.swordcontains(link.getEdge(), link.getSpeed())) {
			bokoblin.setAlive(false);
			numMonsters--;
		}

		ganon.move();
		ganon.draw(g2d);
		if (ganon.contains(link.getBounds()) & link.isVulnerable() == true) {
			hurt = true;
		}

		if (ganon.swordcontains(link.getEdge(), link.getSpeed())) {
			ganon.health -= 10;
			link.req_dx *= -1;
			link.req_dy *= -1;
			link.setSpeed(3);
			if (ganon.getHealth() == 0)
				ganon.setAlive(false);
		}
		if (end == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 19; j++) {
					fire[i][j].draw(g2d);
				}
			}

			for (int i = 1; i < 19; i++) {
				if (fire[0][i].contains(link.getBounds())) {
					link.setY(2 * BLOCK_SIZE);
					link.setSpeed(0);
					link.setBurned(true);
					hearts--;
				}
			}
			for (int i = 1; i < 19; i++) {
				if (fire[1][i].contains(link.getBounds())) {
					link.setX(17 * BLOCK_SIZE);
					link.setSpeed(0);
					link.setBurned(true);
					hearts--;
				}
			}
			for (int i = 1; i < 18; i++) {
				if (fire[2][i].contains(link.getBounds())) {
					link.setY(17 * BLOCK_SIZE);
					link.setSpeed(0);
					link.setBurned(true);
					hearts--;
				}
			}
			for (int i = 1; i < 18; i++) {
				if (fire[3][i].contains(link.getBounds())) {
					link.setX(2 * BLOCK_SIZE);
					link.setSpeed(0);
					link.setBurned(true);
					hearts--;
				}
			}

			if (fire[0][0].contains(link.getBounds())) {
				link.setX(2 * BLOCK_SIZE);
				link.setY(2 * BLOCK_SIZE);
				link.setSpeed(0);
				link.setBurned(true);
				hearts--;
			}
			if (fire[1][0].contains(link.getBounds())) {
				link.setX(17 * BLOCK_SIZE);
				link.setY(2 * BLOCK_SIZE);
				link.setSpeed(0);
				link.setBurned(true);
				hearts--;
			}
			if (fire[2][18].contains(link.getBounds())) {
				hurt = true;
				link.setX(17 * BLOCK_SIZE);
				link.setY(17 * BLOCK_SIZE);
				link.setSpeed(0);
				link.setBurned(true);
				hearts--;
			}
			if (fire[3][18].contains(link.getBounds())) {
				hurt = true;
				link.setX(2 * BLOCK_SIZE);
				link.setY(17 * BLOCK_SIZE);
				link.setSpeed(0);
				link.setBurned(true);
				hearts--;
			}
		}
		if (victory == true) {
			triforce.draw(g2d);
			if (triforce.contains(link.getBounds()) == true) {
				System.out.println("You won!");
				System.exit(1);
			}
		}
	}

	private void drawMap(Graphics2D g2d) {//Draws the map, depending on the screenData and the corner variables initialized earlier
		int r, c;
		Color mazeColor = new Color(105, 105, 105);
		Color wallColor = new Color(0, 0, 0);

		for (r = 0; r < SCREEN_SIZE; r += BLOCK_SIZE) {
			for (c = 0; c < SCREEN_SIZE; c += BLOCK_SIZE) {
				int gr = r / BLOCK_SIZE;
				int gc = c / BLOCK_SIZE;

				g2d.setColor(mazeColor);
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(c, r, c, r + BLOCK_SIZE - 1);
				g2d.drawLine(c, r, c + BLOCK_SIZE - 1, r);

				g2d.setColor(wallColor);
				if ((screenData[gr][gc] & LEFT_CORNER) != 0)
					g2d.drawLine(c, r, c, r + BLOCK_SIZE - 1);

				if ((screenData[gr][gc] & TOP_CORNER) != 0)
					g2d.drawLine(c, r, c + BLOCK_SIZE - 1, r);

				if ((screenData[gr][gc] & RIGHT_CORNER) != 0)
					g2d.drawLine(c + BLOCK_SIZE - 1, r, c + BLOCK_SIZE - 1, r + BLOCK_SIZE - 1);

				if ((screenData[gr][gc] & BOTTOM_CORNER) != 0)
					g2d.drawLine(c, r + BLOCK_SIZE - 1, c + BLOCK_SIZE - 1, r + BLOCK_SIZE - 1);

			}
		}
	}

	public void paintComponent(Graphics g) {//Keeps repainting the map, the hearts, and animates Link and the remaining monsters that are alive
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(new Color(128, 128, 128));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		drawMap(g2d);
		drawHearts(g2d);
		link.animate();
		knight.doAnim();
		moblin.doAnim();
		wallmaster.doAnim();
		stalfos.doAnim();
		bokoblin.doAnim();
		ganon.doAnim();
		if (end == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 19; j++) {
					fire[i][j].doAnim();
				}
			}
		}

		try {
			if (inGame)
				playGame(g2d);
			else
				showIntroScreen(g2d);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Getters and setters for proper encapsulation

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public static int getBlockSize() {
		return BLOCK_SIZE;
	}

	public static int getLeftCorner() {
		return LEFT_CORNER;
	}

	public static int getTopCorner() {
		return TOP_CORNER;
	}

	public static int getRightCorner() {
		return RIGHT_CORNER;
	}

	public static int getBottomCorner() {
		return BOTTOM_CORNER;
	}

	public static int getEmptySpace() {
		return EMPTY_SPACE;
	}
}
