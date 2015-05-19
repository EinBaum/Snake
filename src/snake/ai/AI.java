package snake.ai;

import game.Field;
import snake.Direction;
import snake.GraphicsFieldObject;
import snake.Snake;
import snake.objects.Food;

/**
 *
 * @author EinBaum
 */
public interface AI {
	
	public abstract Direction getDirection(Field<GraphicsFieldObject> field, Food food, Snake snake);
}
