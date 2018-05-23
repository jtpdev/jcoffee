package io.github.jtpdev.jcoffee.exs;

/**
 * @author Jimmy Porto
 *
 */
public enum JCoffeeExceptionType {
	
	CONNECTION_IS_NULL("The connection can not be null."),
	CONNECTION_IS_CLOSED("The connection is closed."),
	ENTITY_IS_NULL("The entity can not be null."),
	;
	
	private String message;

	private JCoffeeExceptionType(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

}
