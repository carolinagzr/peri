package co.com.periferia.commons.dto.api;

/**
 * Clase para ejecutar un runtime exception
 * 
 * @author Periferia IT
 * @version: 1.0
 */
public class PeriferiaRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6041505820702947498L;

	public PeriferiaRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PeriferiaRuntimeException(String message) {
		super(message);
	}

}
