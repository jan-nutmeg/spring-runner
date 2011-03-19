package springrunner.data;

/**
 * An immutable holder of any 2 items.
 * 
 * @author Zemian Deng
 */
public class Tuple2<A, B> {
	private final A _1;

	private final B _2;

	public Tuple2(final A item1, final B item2) {
		this._1 = item1;
		this._2 = item2;
	}

	public A get_1() {
		return _1;
	}

	public B get_2() {
		return _2;
	}

	@Override
	public String toString() {
		return "Tuple2[" + _1 + ", " + _2 + "]";
	}
}
