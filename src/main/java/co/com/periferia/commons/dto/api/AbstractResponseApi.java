package co.com.periferia.commons.dto.api;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.httpclient.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import co.com.periferia.commons.dto.service.HomologacionService;
import co.com.periferia.commons.enums.CodigosRespuestaApi;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Clase base para la respuesta de los microservicios
 * 
 * @author Periferia
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public abstract class AbstractResponseApi {

	/**
	 * Encabezado del mensaje
	 */
	private MsgRsHdr msgRsHdr;
	/**
	 * Fecha y hora de respuesta
	 */
	private String endDt;
	/**
	 * Error
	 */
	private ErrorCode errorCode;

	/**
	 * Construye respuesta exitosa
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi ok(Class<? extends AbstractResponseApi> clazz) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.EXITOSO);
	}

	/**
	 * Construye respuesta de error de negocio
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi warn(Class<? extends AbstractResponseApi> clazz) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.NO_EXITOSO);
	}

	/**
	 * Construye respuesta de error de negocio con el codigo y el mensaje pasados
	 * como parametros
	 * 
	 * @param clazz  clase de respuesta de cada microservicio
	 * @param rtaCod codigo de respuesta
	 * @param rtaMsj mensaje de respuesta
	 * @return
	 */
	public static AbstractResponseApi warn(Class<? extends AbstractResponseApi> clazz, String rtaCod, String rtaMsj) {
		Status status = new Status().setStatusCode(rtaCod).setStatusDesc(rtaMsj)
				.setSeverity(CodigosRespuestaApi.NO_EXITOSO.getSeveridad());
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr);
	}

	/**
	 * Retorna estructura de error de negocio teniendo en cuenta la homologacion
	 * correspondiente al legado destino En caso de no existir el legadoDestino o
	 * que la homologacion no este parametrizada, se devuelve estructura de error de
	 * negocio por defecto
	 * 
	 * @param clazz         clase de respuesta de la API
	 * @param codOrigen     codigo de respuesta que se requiere homologar
	 * @param legadoDestino en caso de estar presente se homologa el valor que se
	 *                      debe enviar al legado especificado
	 * @return
	 */
	public static AbstractResponseApi warnHomol(Class<? extends AbstractResponseApi> clazz,
			HomologacionService svcHomol, String codOrigen, String legadoOrigen, String legadoDestino) {
		Optional<HomologacionCodigoRespuesta> opHomol = svcHomol.homologarCodigoRespuesta(codOrigen, legadoOrigen,
				legadoDestino);
		if (opHomol.isPresent()) {
			HomologacionCodigoRespuesta homologacion = opHomol.get();
			return AbstractResponseApi.warn(clazz, homologacion.getCodigo(), homologacion.getMensaje());
		}
		AbstractResponseApi res = AbstractResponseApi.warn(clazz);
		Status status = res.getMsgRsHdr().getStatus();
		status.setStatusDesc(status.getStatusDesc() + " - Homologacion no encontrada");
		return res;
	}

	/**
	 * Construye respuesta de error interno en la aplicacion
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi internalApiError(Class<? extends AbstractResponseApi> clazz, ApiError e) {
		Status status = new Status().setStatusCode(e.getCodigosRespuestaApi().getRtaCod()).setStatusDesc(e.getMessage())
				.setSeverity(CodigosRespuestaApi.ERROR_TECNICO.getSeveridad());
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		Optional<Throwable> cause = Optional.ofNullable(e.getCause());
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setMoreInformation(cause.orElse(e).getMessage()));
	}

	/**
	 * Construye respuesta de error de validacion de estructura
	 * 
	 * @param clazz  clase de respuesta de cada microservicio
	 * @param errors campos faltantes en el objeto validado
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi validationError(Class<? extends AbstractResponseApi> clazz, List<String> errors) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.VALIDATION_ERROR)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_BAD_REQUEST))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_BAD_REQUEST))
						.setMoreInformation(String.join(",", errors)));
	}

	/**
	 * Construye respuesta de error de validacion de estructura
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param error campos faltante en el objeto validado
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi validationError(Class<? extends AbstractResponseApi> clazz, String error) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.VALIDATION_ERROR)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_BAD_REQUEST))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_BAD_REQUEST)).setMoreInformation(error));
	}

	/**
	 * Construye respuesta de recurso no encontrado
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi error404(Class<? extends AbstractResponseApi> clazz, Throwable e) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.NOT_FOUND_ERROR).setErrorCode(new ErrorCode()
				.setHttpCode(String.valueOf(HttpStatus.SC_NOT_FOUND))
				.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_NOT_FOUND)).setMoreInformation(e.getMessage()));
	}

	/**
	 * Construye respuesta de error general
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi error(Class<? extends AbstractResponseApi> clazz, Throwable e) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.ERROR_TECNICO)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setMoreInformation(e.getMessage()));
	}

	/**
	 * Construye respuesta de recurso no encontrado
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi errorNotFoundEntity(Class<? extends AbstractResponseApi> clazz, String message) {
		Status status = new Status().setStatusCode(CodigosRespuestaApi.NO_EXITOSO.getRtaCod()).setStatusDesc(message)
				.setSeverity(CodigosRespuestaApi.NO_EXITOSO.getSeveridad());
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_NO_CONTENT))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_NO_CONTENT))
						.setMoreInformation(message));
	}

	/**
	 * Construye respuesta de recurso no encontrado
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi businessError(Class<? extends AbstractResponseApi> clazz, String message,
			String code) {
		Status status = new Status().setStatusCode(CodigosRespuestaApi.NO_EXITOSO.getRtaCod())
				.setSeverity(CodigosRespuestaApi.NO_EXITOSO.getSeveridad());
		status.setAdditionalStatus(new AdditionalStatus().setServerStatusCode(code).setStatusDesc(message));
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr);
	}

	/**
	 * Construye respuesta de violiacion de integridad de datos
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi dataIntegrityViolationException(Class<? extends AbstractResponseApi> clazz,
			String message) {
		Status status = new Status().setStatusCode(CodigosRespuestaApi.NO_EXITOSO.getRtaCod()).setStatusDesc(message)
				.setSeverity(CodigosRespuestaApi.NO_EXITOSO.getSeveridad());
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_UNPROCESSABLE_ENTITY))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_UNPROCESSABLE_ENTITY))
						.setMoreInformation(message));
	}

	/**
	 * Retorna respuesta con el encabezado segun sea el tipo de respuesta
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param t     tipo de respuesta {@link TipoRespuesta}
	 * @return instancia de la clase pasada como parametro
	 */
	private static AbstractResponseApi obtenerRespuesta(Class<? extends AbstractResponseApi> clazz,
			CodigosRespuestaApi t) {
		Status status = new Status().setStatusCode(t.getRtaCod()).setStatusDesc(t.getRtaMsj())
				.setSeverity(t.getSeveridad());
		MsgRsHdr msgRsHdr = new MsgRsHdr().setStatus(status);
		return instanciarClase(clazz).setMsgRsHdr(msgRsHdr);
	}

	/**
	 * Instancia el objeto de respuesta
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @return instancia de la clase pasada como parametro
	 */
	private static AbstractResponseApi instanciarClase(Class<? extends AbstractResponseApi> clazz) {
		try {
			return clazz.getConstructor().newInstance().setEndDt(nowAsString());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new PeriferiaRuntimeException("Error instanciado la clase " + clazz.getName());
		}
	}

	/**
	 * Construye respuesta de error general en base de datos
	 * 
	 * @param clazz clase de respuesta de cada microservicio
	 * @param e     error
	 * @return instancia de la clase pasada como parametro
	 */
	public static AbstractResponseApi dataBaseError(Class<? extends AbstractResponseApi> clazz, Throwable e) {
		return obtenerRespuesta(clazz, CodigosRespuestaApi.DATA_BASE_ERROR)
				.setErrorCode(new ErrorCode().setHttpCode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setHttpMessage(HttpStatus.getStatusText(HttpStatus.SC_INTERNAL_SERVER_ERROR))
						.setMoreInformation(e.getMessage()));
	}

	/**
	 * Metodo que retorna la fecha actual del sistema
	 * 
	 * @return String Fecha actual
	 */
	public static OffsetDateTime now() {
		return Instant.now().atOffset(ZoneOffset.ofHours(-5));
	}

	/**
	 * Metodo que retorna la fecha actual del sistema
	 * 
	 * @return String Fecha actual
	 */
	public static String nowAsString() {
		return now().toString();
	}
}