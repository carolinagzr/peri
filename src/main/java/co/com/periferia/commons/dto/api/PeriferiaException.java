package co.com.periferia.commons.dto.api;


/**
 * Clase para el manejo de excepciones personalizado para la aplicacion
 * 
 * @author Periferia IT
 * @version: 1.0
 */
public class PeriferiaException extends RuntimeException {

	private static final long serialVersionUID = -2176665940849097955L;

	private final String codigo;

	private final String message;

	public PeriferiaException(String codigo, String message, Throwable cause) {
		super(message, cause);
		this.codigo = codigo;
		this.message = message;
	}

	public PeriferiaException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
		this.message = message;
	}

	public String getCodigo() {
		return codigo;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
