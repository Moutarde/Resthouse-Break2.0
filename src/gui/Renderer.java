/**
 * 
 */
package gui;

import java.awt.Graphics;


/**
 * @author Nicolas
 * 
 * Inspired from Building Games Using the MVC Pattern - Tutorial and Introduction
 * Author:	impaler@obviam.net
 * Date:	2012.02
 * Link:	http://obviam.net/index.php/the-mvc-pattern-tutorial-building-games/
 *
 */
public interface Renderer {
	public void render(Graphics g);
	public void changeDebugMode();
}
