package snake;

import game.FieldObject;
import java.awt.Color;

/**
 *
 * @author EinBaum
 */
public class GraphicsFieldObject extends FieldObject<GraphicsFieldObject> {

	private Color color;
	
	public GraphicsFieldObject(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
