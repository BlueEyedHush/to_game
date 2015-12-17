package pl.edu.agh.to.game.common.exceptions;

public class ControllerException extends RuntimeException{
	public ControllerException() {
		super();
	}

	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}
}
