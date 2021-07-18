package ZeldaGame;

import java.awt.*;

/**
 * 
 * @author Rohith Perla
 * MoveableShape interface - Link implements this interface
 *
 */

public interface MoveableShape {//Defines basic methods of moving and being drawn
	
	void draw(Graphics2D g2);
	
	void move();

}
