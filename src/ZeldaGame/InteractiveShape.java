package ZeldaGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author Rohith Perla
 * InteractiveShape interface - Fire and Triforce implement this interface
 *
 */

public interface InteractiveShape extends MoveableShape {//Adds on a method to see if an implementing class touches something else
	
	@Override
	void draw(Graphics2D g2);
	
	@Override
	void move();
	
	public boolean contains(Rectangle r);

}
