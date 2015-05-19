/*
 * Copyright (C) 2014 EinBaum
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
public class StupidAI implements AI {

	private Direction dirX;
	public StupidAI() {
		this.dirX = Direction.RIGHT;
	}

	@Override
	public Direction getDirection(Field<GraphicsFieldObject> field, Food food, Snake snake) {
		int x = snake.getHead().getX();
		int y = snake.getHead().getY();
		
		if (y == 0 && x == 1) {
			return Direction.LEFT;
		}
		
		if (x == 0 && y < field.getHeight()-1) {
			dirX = Direction.RIGHT;
			return Direction.DOWN;
		}
		
		if (x == 1 && dirX == Direction.LEFT) {
			dirX = Direction.RIGHT;
			return Direction.UP;
		}
		
		if (x == field.getWidth()-1 && dirX == Direction.RIGHT) {
			dirX = Direction.LEFT;
			return Direction.UP;
		}
		
		return dirX;
	}
}
