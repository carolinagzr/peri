package co.com.periferia.commons.util.traceid;

import java.util.Map;
import java.util.UUID;

import org.slf4j.MDC;

import co.com.periferia.commons.enums.PeriferiaConstants;

/**
 * Clase para configurar los valores del trace id en el MDC
 * 
 * @author Periferia
 *
 */
public class TraceIdContext {

	private TraceIdContext() {
		throw new IllegalStateException("Utility class TraceIdContext");
	}

	/**
	 * Elimina las llaves del trace id del MDC
	 */
	public static void removeTraceIdContext() {
		MDC.remove(PeriferiaConstants.MDC_KEY_RQUID);
		MDC.remove(PeriferiaConstants.MDC_KEY_SPANID);
	}

	/**
	 * Agrega las llaves del trace id en el MDC
	 * 
	 * @param rquid identificador de la peticion
	 */
	public static void setTraceIdContext(String rquid) {

		MDC.put(PeriferiaConstants.MDC_KEY_RQUID, rquid);
		MDC.put(PeriferiaConstants.MDC_KEY_SPANID, UUID.randomUUID().toString());

	}

	/**
	 * Agrega las llaves del trace id en el MDC para varios parametros
	 * 
	 * @param rquid identificador de la peticion
	 */
	public static void setTraceIdContext(Map<String, String> mapParameters) {
		for (Map.Entry<String, String> entry : mapParameters.entrySet())
			MDC.put(entry.getKey(), entry.getValue());
	}

	/**
	 * Agrega las llaves del trace id en el MDC
	 * 
	 * @param rquid  identificador de la peticion
	 * @param spanId
	 */
	public static void setTraceIdContext(String rquid, String spanId) {
		MDC.put(PeriferiaConstants.MDC_KEY_RQUID, rquid);
		MDC.put(PeriferiaConstants.MDC_KEY_SPANID, spanId);
	}

	/**
	 * Obtiene el valor del rquid del MDC
	 * 
	 * @return
	 */
	public static String getRquidFromContext() {
		return MDC.get(PeriferiaConstants.MDC_KEY_RQUID);
	}

	/**
	 * Otiene el valor del spanId del MDC
	 * 
	 * @return
	 */
	public static String getSpanIdFromContext() {
		return MDC.get(PeriferiaConstants.MDC_KEY_SPANID);
	}

	/**
	 * Obtiene el valor del legado origen
	 * 
	 * @return
	 */
	public static String getOrigenFromContext() {
		return MDC.get(PeriferiaConstants.HEADER_LEGADO_ORIGEN);
	}
}
