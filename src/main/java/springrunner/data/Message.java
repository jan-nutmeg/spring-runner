package springrunner.data;

import java.io.Serializable;

/**
 * A data message that hold any payload object and contains key and value header props.
 * 
 * @author Zemian Deng
 */
public class Message<T> implements Serializable {
	/** serialVersionUID - long */
	private static final long serialVersionUID = -4937870711725163021L;

	protected Header header = new Header();

	protected T payload;

	public Message() {
	}

	public Message(T payload) {
		this.payload = payload;
	}

	/**
	 * Getter.
	 * 
	 * @return the header - Header
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * Setter
	 * 
	 * @param header
	 *            Header, the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 * Getter.
	 * 
	 * @return the payload - T
	 */
	public T getPayload() {
		return payload;
	}

	/**
	 * Setter
	 * 
	 * @param payload
	 *            T, the payload to set
	 */
	public void setPayload(T payload) {
		this.payload = payload;
	}

	/**
	 * Override @see java.lang.Object#toString() method.
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		int id = System.identityHashCode(this);
		return "Message@" + id + "[" + payload + ", header=" + header + "]";
	}
}
