package game;

/**
 *
 * @author EinBaum
 */
public class FieldEvent<T extends FieldObject> {
	
	private final int x;
	private final int y;
	private final T oldElement;
	private final T newElement;
	
	public FieldEvent(int x, int y, T oldElement, T newElement) {
		this.x = x;
		this.y = y;
		this.oldElement = oldElement;
		this.newElement = newElement;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public T getOldElement() {
		return oldElement;
	}

	public T getNewElement() {
		return newElement;
	}
	
}
