package io.github.jtpdev.jcoffee.exs;

 /**
 * @author Jimmy Porto
 *
 */
public class JCoffeeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private JCoffeeExceptionType type;

	public JCoffeeException(JCoffeeExceptionType type) {
		super(type.getMessage());
		this.setType(type);
	}

	public JCoffeeExceptionType getType() {
		return type;
	}

	public void setType(JCoffeeExceptionType type) {
		this.type = type;
	}

}
