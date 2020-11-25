package co.com.periferia.commons.enums;

import lombok.Getter;

/**
 * Tipos de respuesta
 * 
 * @author Luis Alejandro Tellez
 */
@Getter
public enum CodigosRespuestaApi {
	
	EXITOSO("0", "Transaccion exitosa", "Info"),
	NO_EXITOSO("206", "Error de negocio", "Warn"),
	ERROR_TECNICO("300", "Error tecnico", CodigosRespuestaApi.SEVERIDAD_ERROR),
	ERROR_TIMEOUT("301", "Error tiempo de espera", CodigosRespuestaApi.SEVERIDAD_ERROR),
	VALIDATION_ERROR("302", "Error de validacion de estructura", CodigosRespuestaApi.SEVERIDAD_ERROR),
	NOT_FOUND_ERROR("303", "Recurso no encontrado", CodigosRespuestaApi.SEVERIDAD_ERROR),
	DATA_BASE_ERROR("304", "Error en base de datos", CodigosRespuestaApi.SEVERIDAD_ERROR);

	public static final String SEVERIDAD_ERROR = "Error";

	private String rtaCod;

	private String rtaMsj;

	private String severidad;

	CodigosRespuestaApi(String rtaCod, String rtaMsj, String severidad) {
		this.rtaCod = rtaCod;
		this.rtaMsj = rtaMsj;
		this.severidad = severidad;
	}
}