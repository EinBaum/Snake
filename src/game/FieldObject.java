package game;

/**
 *
 * @author EinBaum
 */
public class FieldObject<T extends FieldObject> {
	
	private Field<T> field;
	private int x;
	private int y;
	
	public FieldObject() {
		this.field = null;
		this.x = -1;
		this.y = -1;
	}
	
	void setField(Field<T> field) {
		this.field = field;
	}
	void setX(int x) {
		this.x = x;
	}
	void setY(int y) {
		this.y = y;
	}

	public boolean isOnField() {
		return field != null;
	}
	public Field<T> getField() {
		return field;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
