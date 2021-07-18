package ZeldaGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * 
 * @author Rohith Perla
 * Triforce class - Touch this object to win the game
 *
 */

public class Triforce implements InteractiveShape {
	
	//Positional variables
	private int x, y;
	private int blockwidth;

	//Terrain data
	private short screenData[][];

	private Image triforce;
	
	//Bounds for the Triforce to be interacted with
	private Rectangle bounds;

	public Triforce(short screenData[][], int brd_x, int brd_y, int blockwidth) {//Constructor
		this.screenData = screenData;
		this.x = brd_x * blockwidth;
		this.y = brd_y * blockwidth;
		this.blockwidth = blockwidth;
		loadImages();
		bounds = new Rectangle(x, y, 24, 23);
	}

	@Override
	public void draw(Graphics2D g2d) {//Draws the Triforce
		g2d.drawImage(triforce, x, y, null);

	}

	@Override
	public void move() {//Method without any function - necessary because the Triforce needed to be interacted with, but didn't need to move
		// TODO Auto-generated method stub

	}
	
	public boolean contains(Rectangle link) {//Checks to see if the Triforce touches Link
		if (bounds.intersects(link))
			return true;
		else
			return false;
	}

	public void loadImages() {//Loads the image for the Triforce
		triforce = new ImageIcon("Images/Triforce/Triforce.png").getImage();
	}

}
