package game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EinBaum
 */
public class Field<T extends FieldObject> {
	
	private final Object[][] field;
	private final List<FieldEventListener<T>> eventListeners;
	
	public Field(int width, int height) {
		this.field = new Object[width][height];
		this.eventListeners = new ArrayList<>();
	}
	
	public boolean shallowCompare(Field<T> other) {
		if (getWidth() != other.getWidth() || getHeight() != other.getHeight()) {
			return false;
		}
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == other.field[i][j]) {}
				else if (field[i][j] == null || other.field[i][j] == null) return false;
				else if (field[i][j].getClass() == other.field[i][j].getClass()) {}
				else return false;
			}
		}
		return true;
	}
	
	public void addEventListener(FieldEventListener<T> eventListener) {
		this.eventListeners.add(eventListener);
	}
	public boolean removeEventListener(FieldEventListener<T> eventListener) {
		return this.eventListeners.remove(eventListener);
	}
	
	public int getWidth() {
		return field.length;
	}
	public int getHeight() {
		return field[0].length;
	}
	
	private void uncheckedSet(int x, int y, T newElement) {
		
		T oldElement = (T)this.field[x][y];
		if (oldElement != null) {
			oldElement.setField(null);
			oldElement.setX(-1);
			oldElement.setY(-1);
		}
		
		this.field[x][y] = newElement;
		if (newElement != null) {
			newElement.setField(this);
			newElement.setX(x);
			newElement.setY(y);
		}
		
		FieldEvent e = new FieldEvent<>(x, y, oldElement, newElement);
		this.eventListeners.forEach(el -> el.onChange(e));
	}
	public synchronized void set(int x, int y, T newElement) {
		if (newElement != null && newElement.isOnField()) {
			throw new IllegalArgumentException("Element is already on a field.");
		}
		uncheckedSet(x, y, newElement);
	}
	public synchronized T get(int x, int y) {
		return (T)this.field[x][y];
	}
	public synchronized void swap(int x, int y, int otherX, int otherY) {
		T ele1 = get(x, y);
		T ele2 = get(otherX, otherY);
		
		this.field[x][y] = null;
		this.field[otherX][otherY] = null;
		
		uncheckedSet(x, y, ele2);
		uncheckedSet(otherX, otherY, ele1);
	}
	public synchronized void move(int x, int y, int otherX, int otherY) {
		T ele = get(x, y);
		uncheckedSet(x, y, null);
		uncheckedSet(otherX, otherY, ele);
	}
}
