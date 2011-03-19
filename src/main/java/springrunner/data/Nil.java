package springrunner.data;

/**
 * There is only one instance of Nil.VALUE, and it represents nothing. It's like void.
 * 
 * <p>
 * This is useful for supplying type to those generic that must requires a type, and yet it's not used.
 * 
 * <p>
 * Here is a silly example: Tuple2 single = new Tuple2(new Name("first"), Nil.VALUE)
 * 
 * @author Zemian Deng
 */
public final class Nil {
	public static Nil VALUE = new Nil();

	private Nil() {
	}

	@Override
	public String toString() {
		return "Nil";
	}
}
