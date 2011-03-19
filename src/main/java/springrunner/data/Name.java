package springrunner.data;

/**
 * A name with a string value.
 * 
 * @author Zemian Deng
 */
public class Name {
	private String value;

	public Name() {
	}

	public Name(String value) {
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Name[" + value + "]";
	}
}
