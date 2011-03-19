package springrunner.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Message}'s header that provide key and value data map.
 * 
 * @author Zemian Deng
 */
public class Header implements Serializable {
	/** serialVersionUID - long */
	private static final long serialVersionUID = -8871102778079116272L;

	private Map<String, Object> props = new HashMap<String, Object>();

	public void add(String name, Object value) {
		props.put(name, value);
	}

	public Object get(String name) {
		return props.get(name);
	}

	@Override
	public String toString() {
		return props.toString();
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}
}
