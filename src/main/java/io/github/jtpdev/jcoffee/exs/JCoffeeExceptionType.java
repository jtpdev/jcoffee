package io.github.jtpdev.jcoffee.exs;

/**
 * @author Jimmy Porto
 *
 */
public enum JCoffeeExceptionType {
	
	CONNECTION_IS_NULL("The connection can not be null."),
	CONNECTION_IS_CLOSED("The connection is closed."),
	ENTITY_IS_NULL("The entity can not be null."),
	ENTITY_ID_IS_NULL("The entity identifier can not be null in this statement."),
	ENTITY_NOT_IS_ISNTANCE_OF_JCOFFEEENTITY("The entity must be implement JCoffeeEntity."),
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
