package co.com.periferia.commons.enums;

/**
 * Clase para las constantes de la aplicacion bill pay Ath
 * 
 * @author Periferia IT
 * @version: 1.0
 */
public final class PeriferiaConstants {

	private PeriferiaConstants() {
		throw new IllegalStateException("Utility class PeriferiaConstants");
	}

	/**
	 * LLamados servicio rest WSDL
	 */
	public static final String ID_CONSULTAR_FACTURA_REST = "Consultar Factura por REST ws";

	/**
	 * Formatos de fechas
	 */
	public static final String FORMATO_FECHA_HORA_TRANSMISION = "MMddhhmmss";

	/**
	 * Errores
	 */
	public static final String NOMBRE_ERROR = "Error";

	/**
	 * Cabecera HTTP donde se encuentra el rquid
	 */
	public static final String HEADER_RQUID = "X-RqUID";

	/**
	 * Cabecera HTTP donde se encuentra el legado origen
	 */
	public static final String HEADER_LEGADO_ORIGEN = "X-Origen";

	/**
	 * Cabecera HTTP donde se encuentra el nura convenio
	 */
	public static final String HEADER_NURA_CONVENIO = "X-NuraConvenio";

	/**
	 * Id legado Postilion en la tabla APLICACIONES
	 */
	public static final String LEGADO_POSTILION = "1";

	/**
	 * Id legado ESB en la tabla APLICACIONES
	 */
	public static final String LEGADO_ESB = "2";

	/**
	 * Informacion del log para rquid
	 */
	public static final String MDC_KEY_RQUID = "rquid";

	/**
	 * Informacion del log para spanId
	 */
	public static final String MDC_KEY_SPANID = "spanId";

	/**
	 * Informacion del log para nuraConvenio
	 */
	public static final String MDC_KEY_NURA_CONVENIO = "nuraConvenio";

	/**
	 * Severidad
	 */
	public static final String SEVERITY_INFO = "Info";
	public static final String SEVERITY_WARN = "Warn";
	public static final String SEVERITY_ERROR = "Error";

	/**
	 * Perfiles de ejecucion
	 */

	public static final String PROFILE_DEFAULT = "default";
	public static final String PROFILE_KUBERNETES = "kubernetes";
}
