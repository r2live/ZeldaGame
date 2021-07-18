package ZeldaGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * 
 * @author Rohith Perla
 * Ganon class - The final boss of the game - defeat him to open up the path to victory
 *
 */

public class Ganon implements MonsterShape {
	
	//Positional variables
	private int x, y;
	private int dx, dy;
	private int blockwidth;

	private int speed = 0;
	
	//screenData from the ZeldaBoard class so that Ganon can detect collisions accurately
	private short screenData[][];

	private boolean alive;

	private Image[] ganon = new Image[3];

	
	//Variables for animation
	private int spriteDelay = 7;
	private int spriteNumPos = 3;
	private int spriteDelayCount = spriteDelay;
	private int spriteIncr = 1;
	private int spriteImgIdx = 0;
	
	//Bounds for Ganon to be interacted with
	private Rectangle bounds;
	
	int health = 30;

	public Ganon(short screenData[][], int brd_x, int brd_y, int blockwidth) {
		this.screenData = screenData;
		this.x = brd_x * blockwidth;
		this.y = brd_y * blockwidth;
		this.blockwidth = blockwidth;
		loadImages();
		alive = true;
	}

	public void move() {//Moves Ganon somewhat randomly
		if (alive == true) {
			int count = 0;
			int px, py;

			int sdx[] = new int[4];
			int sdy[] = new int[4];

			if (x % blockwidth == 0 && y % blockwidth == 0) {
				px = x / blockwidth;
				py = y / blockwidth;

				count = 0;

				if ((screenData[py][px] & 1) == 0 && dx != 1) // Moves left
				{
					sdx[count] = -1;
					sdy[count] = 0;
					count++;
				}

				if ((screenData[py][px] & 2) == 0 && dy != 1) // Moves up
				{
					sdx[count] = 0;
					sdy[count] = -1;
					count++;
				}

				if ((screenData[py][px] & 4) == 0 && dx != -1) // Moves right
				{
					sdx[count] = 1;
					sdy[count] = 0;
					count++;
				}

				if ((screenData[py][px] & 8) == 0 && dy != -1) // Moves down
				{
					sdx[count] = 0;
					sdy[count] = 1;
					count++;
				}

				if (count != 0) {
					count = (int) (Math.random() * count); //

					dx = sdx[count];
					dy = sdy[count];
				} else {
					dx = -dx;
					dy = -dy;
				}

			}

			x = x + (dx * speed);
			y = y + (dy * speed);
		}
	}

	public boolean contains(Rectangle link) { //Checks to see if the monster touches Link
		if (alive == true) {
			bounds = new Rectangle(x, y, 30, 32);
			if(bounds.intersects(link))
				return true;
			else
				return false;
		} else
			return false;
	}

	public boolean swordcontains(Rectangle edge, int speed) { //Checks to see if the monster touches Link's sword
		if (alive == true & speed == 0) {
			bounds = new Rectangle(x, y, 30, 32);
			if(bounds.intersects(edge))
				return true;
			else
				return false;
		} else
			return false;
	}

	public void doAnim() { //Animates Ganon - same method from the midterm
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

	public void draw(Graphics2D g2d) { //Draws Ganon
		if (alive == true)
			g2d.drawImage(ganon[spriteImgIdx], x + 1, y + 1, null);
	}

	public void loadImages() { //Loads necessary images for Ganon to be animated
		ganon[0] = new ImageIcon("Images/Ganon/Ganon1.png").getImage();
		ganon[1] = new ImageIcon("Images/Ganon/Ganon2.png").getImage();
		ganon[2] = new ImageIcon("Images/Ganon/Ganon3.png").getImage();
	}
	
	//Getters and setters for proper encapsulation
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public short[][] getScreenData() {
		return screenData;
	}

	public void setScreenData(short[][] screenData) {
		this.screenData = screenData;
	}

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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
