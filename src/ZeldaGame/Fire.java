package ZeldaGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * 
 * @author Rohith Perla
 * Fire class - Implementation for the fire that pops up around the edges of the map when you fight Ganon
 *
 */

public class Fire implements InteractiveShape {
	
	//Positional variables
	private int x, y;
	private int dx, dy;
	private int blockwidth;

	private int speed = 2;
	
	//Terrain data
	private short screenData[][];

	private boolean alive;

	private Image[] fire = new Image[2];
	
	//Animation variables
	private int spriteDelay = 10;
	private int spriteNumPos = 2;
	private int spriteDelayCount = spriteDelay;
	private int spriteIncr = 1;
	private int spriteImgIdx = 0;
	
	//Bounds for the fire to be interacted with
	private Rectangle bounds;
	
	public Fire(short screenData[][], int brd_x, int brd_y, int blockwidth) {//Constructor
		this.screenData = screenData;
		this.x = brd_x * blockwidth;
		this.y = brd_y * blockwidth;
		this.blockwidth = blockwidth;
		loadImages();
		alive = true;
	}

	@Override
	public void draw(Graphics2D g2d) {//Draws the fire depending on animation
		if(alive == true)
			g2d.drawImage(fire[spriteImgIdx], x + 1, y + 1, null);

	}

	@Override
	public void move() {//No functionality - necessary because the fire needs to be interacted with but doesn't need to be moved
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Rectangle link) {//Checks to see if the fire touches Link
		if (alive == true) {
			bounds = new Rectangle(x, y, 24, 26);
			if(bounds.intersects(link))
				return true;
			else
				return false;
		} else
			return false;
	}
	
	public void doAnim() {//Animates the fire - same method from midterm
		if (alive == true) {
			spriteDelayCount--;

			if (spriteDelayCount <= 0) {
				spriteDelayCount = spriteDelay;
				spriteImgIdx = spriteImgIdx + spriteIncr;

				if (spriteImgIdx == (spriteNumPos - 1) || spriteImgIdx == 0) {
					spriteIncr = -spriteIncr;
				}
			}
		}
	}
	
	public void loadImages() {//Loads images
		fire[0] = new ImageIcon("Images/Fire/Fire1.png").getImage();
		fire[1] = new ImageIcon("Images/Fire/Fire2.png").getImage();
	}
	
	//Getters and setters for proper encapsulation

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
