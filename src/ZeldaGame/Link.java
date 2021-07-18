package ZeldaGame;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Rohith Perla
 * Link class - Player character that the user controls
 *
 */

public class Link implements MoveableShape {

	private int x, y;//Position of Link
	private int dx, dy;//How much in a direction Link is moving
	int req_dx, req_dy;//Different combinations of values for these two define the direction Link is going
	private int direction_x, direction_y;
	private int speed = 3;
	private Image link[][] = new Image[4][2]; //Images for animating Link
	private Image injured[][] = new Image[4][2]; //Images for animating Link while he's injured
	private Image s[] = new Image[4]; //Images for Link's sword
	private Image heart;
	private short screenData[][];
	//The next 5 variables are used in the animate method to animate Link at a certain speed
	private int spriteDelay = 7;
	private int spriteNumPos = 2;
	private int spriteDelayCount = spriteDelay;
	private int spriteIncr = 1;
	private int spriteImgIdx = 0;
	private BufferedImage sword, usword, dsword, lsword, rsword; //Images for Link's sword in different directions
	private Rectangle edge; //Bounds for Link's sword
	private Rectangle bounds; //Bounds for Link
	private int swordx, swordy;
	private boolean vulnerable = true; //Checks if Link is able to be damaged
	private boolean burned; //Checks if Link got hit by the fire in phase 2 of the game, and there is code to stop him if he did

	public Link(short screenData[][], int brd_x, int brd_y, int req_dx, int req_dy, int direction_x,
			int direction_y) throws IOException {
		this.x = brd_x;
		this.y = brd_y;
		this.screenData = screenData;
		this.req_dx = req_dx;
		this.req_dy = req_dy;
		this.direction_x = direction_x;
		this.direction_y = direction_y;
		loadImages();
	}

	@Override
	public void draw(Graphics2D g2d) {//Draws Link depending on various conditions being fulfilled
		if (direction_y == -1) {
			if(vulnerable == false)
				g2d.drawImage(injured[0][spriteImgIdx], x + 1, y + 1, null);
			else
				g2d.drawImage(link[0][spriteImgIdx], x + 1, y + 1, null);
			if (speed == 0 & burned == false) {
				g2d.drawImage(s[0], x + 4, y - 16, null);
				sword = usword;
				swordx = x + 4;
				swordy = y - 16;
				edge = new Rectangle(swordx, swordy, sword.getWidth(), sword.getHeight());
			}
		}

		else if (direction_y == 1) {
			if(vulnerable == false)
				g2d.drawImage(injured[1][spriteImgIdx], x + 1, y + 1, null);
			else
				g2d.drawImage(link[1][spriteImgIdx], x + 1, y + 1, null);
			if (speed == 0 & burned == false) {
				g2d.drawImage(s[1], x + 4, y + 16, null);
				sword = dsword;
				swordx = x + 4;
				swordy = y + 16;
				edge = new Rectangle(swordx, swordy, sword.getWidth(), sword.getHeight());
			}
		}

		else if (direction_x == -1) {
			if(vulnerable == false)
				g2d.drawImage(injured[2][spriteImgIdx], x + 1, y + 1, null);
			else
				g2d.drawImage(link[2][spriteImgIdx], x + 1, y + 1, null);
			if (speed == 0 & burned == false) {
				g2d.drawImage(s[2], x - 16, y + 4, null);
				sword = lsword;
				swordx = x - 16;
				swordy = y + 4;
				edge = new Rectangle(swordx, swordy, sword.getWidth(), sword.getHeight());
			}
		}

		else {
			if(vulnerable == false)
				g2d.drawImage(injured[3][spriteImgIdx], x + 1, y + 1, null);
			else
				g2d.drawImage(link[3][spriteImgIdx], x + 1, y + 1, null);
			if (speed == 0 & burned == false) {
				g2d.drawImage(s[3], x + 16, y + 4, null);
				sword = rsword;
				swordx = x + 16;
				swordy = y + 4;
				edge = new Rectangle(swordx, swordy, sword.getWidth(), sword.getHeight());
			}
		}
	}

	@Override
	public void move() {//Moves Link
		int px, py;
		short ch;
		if (req_dx == -dx && req_dy == -dy) {
			dx = req_dx;
			dy = req_dy;
			direction_x = dx;
			direction_y = dy;
		}

		if (x % ZeldaBoard.getBlockSize() == 0 && y % ZeldaBoard.getBlockSize() == 0) {
			px = x / ZeldaBoard.getBlockSize();
			py = y / ZeldaBoard.getBlockSize();
			ch = screenData[py][px];

			// Continuously updates position of Pac-Man while he isn't hitting a wall
			if (req_dx != 0 || req_dy != 0) {
				if (!((req_dx == -1 && req_dy == 0 && (ch & ZeldaBoard.getLeftCorner()) != 0)
						|| (req_dx == 1 && req_dy == 0 && (ch & ZeldaBoard.getRightCorner()) != 0)
						|| (req_dx == 0 && req_dy == -1 && (ch & ZeldaBoard.getTopCorner()) != 0)
						|| (req_dx == 0 && req_dy == 1 && (ch & ZeldaBoard.getBottomCorner()) != 0))) {
					dx = req_dx;
					dy = req_dy;
					direction_x = dx;
					direction_y = dy;
				}
			}
			// Check for standstill
			if ((dx == -1 && dy == 0 && (ch & ZeldaBoard.getLeftCorner()) != 0)
					|| (dx == 1 && dy == 0 && (ch & ZeldaBoard.getRightCorner()) != 0)) {
				dx = 0;
			} else if ((dx == 0 && dy == -1 && (ch & ZeldaBoard.getTopCorner()) != 0)
					|| (dx == 0 && dy == 1 && (ch & ZeldaBoard.getBottomCorner()) != 0)) {
				dy = 0;
			}
		}
		x = x + speed * dx;
		y = y + speed * dy;
		bounds = new Rectangle(x, y, 16, 16);
	}

	public void animate() {//Animates Link
		spriteDelayCount--;

		if (spriteDelayCount <= 0) {
			spriteDelayCount = spriteDelay;
			spriteImgIdx = spriteImgIdx + spriteIncr;

			if (spriteImgIdx == (spriteNumPos - 1) || spriteImgIdx == 0) {
				spriteIncr = -spriteIncr;
			}
		}
	}

	public void loadImages() throws IOException {//Loads necessary images for Link, his sword, and a heart
		link[0][0] = new ImageIcon("Images/Link/LinkU1.png").getImage();
		link[0][1] = new ImageIcon("Images/Link/LinkU2.png").getImage();

		link[1][0] = new ImageIcon("Images/Link/LinkD1.png").getImage();
		link[1][1] = new ImageIcon("Images/Link/LinkD2.png").getImage();

		link[2][0] = new ImageIcon("Images/Link/LinkL1.png").getImage();
		link[2][1] = new ImageIcon("Images/Link/LinkL2.png").getImage();

		link[3][0] = new ImageIcon("Images/Link/LinkR1.png").getImage();
		link[3][1] = new ImageIcon("Images/Link/LinkR2.png").getImage();
		
		injured[0][0] = new ImageIcon("Images/Link/LinkIU1.png").getImage();
		injured[0][1] = new ImageIcon("Images/Link/LinkIU2.png").getImage();
		
		injured[1][0] = new ImageIcon("Images/Link/LinkID1.png").getImage();
		injured[1][1] = new ImageIcon("Images/Link/LinkID2.png").getImage();
		
		injured[2][0] = new ImageIcon("Images/Link/LinkIL1.png").getImage();
		injured[2][1] = new ImageIcon("Images/Link/LinkIL2.png").getImage();
		
		injured[3][0] = new ImageIcon("Images/Link/LinkIR1.png").getImage();
		injured[3][1] = new ImageIcon("Images/Link/LinkIR2.png").getImage();

		s[0] = new ImageIcon("Images/Link/USword.png").getImage();
		s[1] = new ImageIcon("Images/Link/DSword.png").getImage();
		s[2] = new ImageIcon("Images/Link/LSword.png").getImage();
		s[3] = new ImageIcon("Images/Link/RSword.png").getImage();

		heart = new ImageIcon("Images/Link/Heart.png").getImage();
		usword = ImageIO.read(new File("Images/Link/USword.png"));
		dsword = ImageIO.read(new File("Images/Link/DSword.png"));
		lsword = ImageIO.read(new File("Images/Link/LSword.png"));
		rsword = ImageIO.read(new File("Images/Link/RSword.png"));
	}
	
	//Getters and setters for proper encapsulation
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Image getHeart() {
		return heart;
	}

	public void setHeart(Image heart) {
		this.heart = heart;
	}

	public short[][] getScreenData() {
		return screenData;
	}

	public void setScreenData(short[][] screenData) {
		this.screenData = screenData;
	}

	public Rectangle getEdge() {
		return edge;
	}

	public void setEdge(Rectangle edge) {
		this.edge = edge;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public boolean isBurned() {
		return burned;
	}

	public void setBurned(boolean burned) {
		this.burned = burned;
	}

}
