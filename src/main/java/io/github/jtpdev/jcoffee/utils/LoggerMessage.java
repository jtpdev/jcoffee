package io.github.jtpdev.jcoffee.utils;

public enum LoggerMessage {
	
	
	SAVE_START("Started the save process."),
	SAVE_END("Complete the save process."),
	;
	
	private String message;

	private LoggerMessage(String message) {
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
