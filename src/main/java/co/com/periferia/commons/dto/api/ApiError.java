package co.com.periferia.commons.dto.api;

import java.util.Optional;

import co.com.periferia.commons.enums.CodigosRespuestaApi;
import lombok.Getter;

/**
 * Clase que representa un error interno de la aplicacion
 * 
 * @author Periferia IT
 *
 */
public class ApiError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Codigo de excepcion
	 */
	@Getter
	private final CodigosRespuestaApi codigosRespuestaApi;

	/**
	 * Construye respuesta de error
	 * 
	 * @param message de error
	 * @return {@link ApiError}
	 */
	public static ApiError error(String message) {
		return new ApiError(message);
	}

	/**
	 * Construye respuesta de error
	 * 
	 * @param message de error
	 * @param cause   del error
	 * @return {@link ApiError}
	 */
	public static ApiError error(String message, Throwable cause) {
		return new ApiError(message, cause);
	}

	/**
	 * Construye respuesta de timeout
	 * 
	 * @param mensaje de error
	 * @return {@link ApiError}
	 */
	public static ApiError timeout(Optional<String> mensaje) {
		return new ApiError(mensaje.orElseGet(() -> "Timeout"), CodigosRespuestaApi.ERROR_TIMEOUT);
	}

	public ApiError(String message) {
		super(message);
		this.codigosRespuestaApi = CodigosRespuestaApi.ERROR_TECNICO;
	}

	public ApiError(String message, Throwable cause) {
		super(message, cause);
		this.codigosRespuestaApi = CodigosRespuestaApi.ERROR_TECNICO;
	}

	public ApiError(String message, CodigosRespuestaApi cod) {
		super(message);
		this.codigosRespuestaApi = cod;
	}

	public ApiError(String message, Throwable cause, CodigosRespuestaApi cod) {
		super(message, cause);
		this.codigosRespuestaApi = cod;
	}

	public static BadRequestError badRequest(String message) {
		return new BadRequestError(message);
	}

	public static class BadRequestError extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public BadRequestError(String message) {
			super(message);
		}
	}
}
