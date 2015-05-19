package snake;

/**
 *
 * @author EinBaum
 */
public enum Direction {
	UP,
	RIGHT,
	DOWN,
	LEFT;
	
	public Direction next() {
		return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
	}
	public Direction previous() {
		return Direction.values()[(this.ordinal() + 3) % Direction.values().length];
	}
	public Direction opposite() {
		return Direction.values()[(this.ordinal() + 2) % Direction.values().length];
	}
}
