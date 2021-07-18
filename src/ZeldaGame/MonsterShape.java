package ZeldaGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author Rohith Perla
 * MonsterShape interface - Ganon, Knight, Moblin, Wallmaster, Stalfos, and Bokoblin implement this interface
 *
 */

public interface MonsterShape extends InteractiveShape {//Adds another method to see if the monster interacted with Link's sword if Link stopped and attacked

	@Override
	public void draw(Graphics2D g2);

	@Override
	public void move();

	public boolean contains(Rectangle r); 
	
	public boolean swordcontains(Rectangle e, int s);

}
