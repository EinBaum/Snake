package game;

/**
 *
 * @author EinBaum
 */
public interface FieldEventListener<T extends FieldObject> {
	
	public void onChange(FieldEvent<T> e);
}
