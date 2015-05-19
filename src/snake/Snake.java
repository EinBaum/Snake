package snake;

import snake.objects.SnakePart;
import snake.objects.Food;
import snake.objects.SnakeHead;
import game.Field;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author EinBaum
 */
public class Snake {
	
	public static enum MoveResult {
		MOVED,
		ATE_FOOD,
		CRASHED_WALL,
		CRASHED_SELF
	}
	
	private final Field<GraphicsFieldObject> field;
	private final SnakeHead head;
	private final List<SnakePart> parts;
	
	private Direction lastDirection;
	private Direction direction;
	
	public Snake(Field<GraphicsFieldObject> field, int initialSize) {
		this.field = field;
		this.head = new SnakeHead();
		this.parts = new LinkedList<>();
		
		this.lastDirection = Direction.RIGHT;
		this.direction = Direction.RIGHT;
		
		int centerX = field.getWidth() / 2;
		int centerY = field.getHeight() / 2;
		
		field.set(centerX, centerY, head);
		for (int i = 0; i < initialSize-1; i++) {
			SnakePart part = new SnakePart();
			parts.add(part);
			field.set(centerX - (i+1), centerY, part);
		}
	}
	private Snake(Field<GraphicsFieldObject> field, SnakeHead head,
			List<SnakePart> parts, Direction lastDirection, Direction direction) {
		this.field = field;
		this.head = head;
		this.parts = parts;
		this.lastDirection = lastDirection;
		this.direction = direction;
	}
	
	public Snake getCopy(Field<GraphicsFieldObject> newField) {
		
		SnakeHead newHead = new SnakeHead();
		newField.set(head.getX(), head.getY(), newHead);
		
		List<SnakePart> newParts = new LinkedList<>();
		parts.forEach(p -> {
			SnakePart newPart = new SnakePart();
			newParts.add(newPart);
			newField.set(p.getX(), p.getY(), newPart);
		});
		
		return new Snake(newField, newHead, newParts, lastDirection, direction);
	}
	
	public int getLength() {
		return this.parts.size() + 1;
	}
	public SnakeHead getHead() {
		return head;
	}
	public SnakePart getTail() {
		return parts.get(0);
	}
	public void setDirection(Direction direction) {
		if (direction == this.lastDirection.opposite()) {
			throw new IllegalArgumentException("Can't move backwards");
		}
		this.direction = direction;
	}
	public Direction getDirection() {
		return this.direction;
	}
	public Direction getLastDirection() {
		return this.lastDirection;
	}
	public MoveResult tryMove() {
		this.lastDirection = direction;
		
		int oldX = head.getX();
		int oldY = head.getY();
		int nextX = nextX();
		int nextY = nextY();
		
		if (nextX != oldX) {
			if (nextX < 0 || nextX >= field.getWidth()) {
				return MoveResult.CRASHED_WALL;
			}
		} else {
			if (nextY < 0 || nextY >= field.getHeight()) {
				return MoveResult.CRASHED_WALL;
			}
		}
		
		GraphicsFieldObject obj = field.get(nextX, nextY);
		
		if (obj == null) {
			
			field.move(oldX, oldY, nextX, nextY);
			
			if (!parts.isEmpty()) {
				SnakePart lastPart = parts.remove(0);
				parts.add(lastPart);
				field.move(lastPart.getX(), lastPart.getY(), oldX, oldY);
			}
			return MoveResult.MOVED;
			
		} else if (obj instanceof SnakePart && (SnakePart)obj == parts.get(0)) {
			
			parts.add(parts.remove(0));
			field.swap(oldX, oldY, nextX, nextY);
			return MoveResult.MOVED;
			
		} else if (obj instanceof Food) {
			
			field.move(oldX, oldY, nextX, nextY);
			
			SnakePart newPart = new SnakePart();
			parts.add(newPart);
			field.set(oldX, oldY, newPart);
			return MoveResult.ATE_FOOD;
			
		} else {
			return MoveResult.CRASHED_SELF;
		}
	}
	
	private int nextX() {
		int x = head.getX();
		return direction == Direction.LEFT ? x-1
				: direction == Direction.RIGHT ? x+1
				: x;
	}
	private int nextY() {
		int y = head.getY();
		return direction == Direction.UP ? y-1
				: direction == Direction.DOWN ? y+1
				: y;
	}
}
