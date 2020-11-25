package co.com.periferia.commons.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.periferia.commons.dto.api.PeriferiaRuntimeException;
import co.com.periferia.commons.enums.PeriferiaConstants;

/**
 * Clase utilitaria con metodos utilizados para todas las api de Ath Billpay
 * 
 * @author Alejandro Ospina
 * @version: 1.0
 */
public class PeriferiaUtil {

	private PeriferiaUtil() {
		throw new IllegalStateException("Class PeriferiaUtil");
	}

	private static final Logger LOG = LoggerFactory.getLogger(PeriferiaUtil.class);

	private static final String MSJ_ERROR_DATATYPE_CONFIG = "Error obteniendo instancia del DatatypeFactory";

	private static final String FORMATO_FECHA_ANO_MES_DIA = "yyyyMMdd";

	private static Random random = new Random();

	/**
	 * Metodo para converti texto a formato fecha de java en el formato
	 * yyyyMMddHHmmss y yyyyMMdd
	 * 
	 * @param fecha       En formato texto
	 * @param tipoformato condicional para validar tipo de formato
	 * 
	 * @return Date con el formato de la fecha convertido a Date
	 */
	public static Date convertirFecha(String fecha, boolean tipoformato) {
		SimpleDateFormat formatter;
		Date date = null;
		if (tipoformato)
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		else
			formatter = new SimpleDateFormat(FORMATO_FECHA_ANO_MES_DIA);
		try {
			date = formatter.parse(fecha);
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return date;
	}

	/**
	 * Metodo encargado de obtener el mensaje de error y su traza.
	 * 
	 * @param e Error o causa
	 * 
	 * @return String Error en formato texto
	 */
	public static String getStackTraceMessage(Throwable e) {
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
			e.printStackTrace(pw);
			return Arrays.asList(sw.toString().split("\n")).stream().limit(10).collect(Collectors.joining("\n"));
		} catch (Exception ex) {
			return e.getMessage();
		}
	}

	

	/**
	 * Metodo que retorna la fecha actual del sistema en formato MMddhhmmss
	 * 
	 * @return String Fecha actual
	 */
	public static String getSystemCurrentDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(PeriferiaConstants.FORMATO_FECHA_HORA_TRANSMISION);
		return formatter.format(new Date());
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

	/**
	 * Metodo que genera un random entero con el rango maximo de un entero
	 * 
	 * @return int numero aleatorio
	 */
	public static int getRandomInt() {
		return random.nextInt() & Integer.MAX_VALUE;
	}

	/**
	 * Metodo que obtiene la fecha actual en formato XMLGregorianCalendar
	 * 
	 * @return XMLGregorianCalendar Fecha actual
	 */
	public static XMLGregorianCalendar getCurrentDate() {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(now().toString());
		} catch (DatatypeConfigurationException e) {
			throw new PeriferiaRuntimeException(MSJ_ERROR_DATATYPE_CONFIG);
		}
	}

	/**
	 * Metodo que convierte la fecha en formato String a XMLGregorianCalendar
	 * 
	 * @param fecha fecha en string a cambiar
	 * @param sdf   SimpleDateFormat formato de la fecha
	 * 
	 * @return XMLGregorianCalendar fecha cambiada
	 */
	public static XMLGregorianCalendar getDate(String fecha, SimpleDateFormat sdf) {
		GregorianCalendar cal = new GregorianCalendar();
		try {
			cal.setTime(sdf.parse(fecha));
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (ParseException e) {
			throw new PeriferiaRuntimeException(
					"El valor de la fecha [" + fecha + "] no puede ser parseado al formato [" + sdf.toPattern() + "]");
		} catch (DatatypeConfigurationException e) {
			throw new PeriferiaRuntimeException(MSJ_ERROR_DATATYPE_CONFIG);
		}
	}

	public static XMLGregorianCalendar getDate(String fecha) {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(fecha);
		} catch (DatatypeConfigurationException e) {
			throw new PeriferiaRuntimeException(MSJ_ERROR_DATATYPE_CONFIG);
		}
	}

	/**
	 * Metodo que convierte fecha a XMLGregorianCalendar
	 * 
	 * @param ldt LocalDateTime a formatear
	 * 
	 * @return XMLGregorianCalendar fecha cambiada
	 */
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDateTime ldt) {
		try {
			return DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		} catch (DatatypeConfigurationException e) {
			throw new PeriferiaRuntimeException(MSJ_ERROR_DATATYPE_CONFIG);
		}
	}

	/**
	 * Metodo que convierte una fecha en formato date a string
	 * 
	 * @param date   fecha a cambiar
	 * @param format formato de la fecha a cambiar
	 * 
	 * @return String fecha cambiada
	 */
	public static String dateToString(Date date, Optional<String> format) {
		String formatDate = format.orElse("yyyy-MM-dd'T'HH:mm:ss");
		DateFormat df = new SimpleDateFormat(formatDate);
		return df.format(date);
	}

	/**
	 * Metodo que convierte un objecto a formato json
	 * 
	 * @param object objeto a cambiar
	 * 
	 * @return byte[] formato json generado
	 */
	public static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}



	/**
	 * 
	 * @param numCeros
	 * @param cadena
	 * @return
	 */
	public static String agregarCeroIzq(int numCeros, String cadena) {
		StringBuilder ceros = new StringBuilder();
		String result = null;
		for (int i = 0; i < numCeros; i++) {
			ceros.append("0");
		}
		ceros.append(cadena);
		result = ceros.toString();
		return result;
	}

	/**
	 * 
	 * @param numEspacios
	 * @param cadena
	 * @return
	 */
	public static String agregarEspaciosIzq(int numEspacios, String cadena) {
		StringBuilder espacios = new StringBuilder();
		String result = null;
		for (int i = 1; i < numEspacios; i++) {
			espacios.append(" ");
		}
		espacios.append(cadena);
		result = espacios.toString();
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerFechaFormatoAMD() {
		return new SimpleDateFormat(FORMATO_FECHA_ANO_MES_DIA).format(new Date());
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerFechaFormatoAMDArchivo() {
		return new SimpleDateFormat(FORMATO_FECHA_ANO_MES_DIA).format(new Date()).replace(":", "");
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerHoraFormatoHM() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerHoraFormatoHMArchivo() {
		return new SimpleDateFormat("HH:mm").format(new Date()).replace(":", "");
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerHoraFormatoHMS() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerHoraFormatoHMSArchivo() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date()).replace(":", "");
	}

	/**
	 * 
	 * @return
	 */
	public static String obtenerCadenaFechaAAAAMMDDHHMMSS() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}

	/**
	 * 
	 * @param fecha
	 * @return
	 */
	public static String convertirFechaAAAAMMDDHHMMSS(Date fecha) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha);
	}

	/**
	 * Metodo que sirve para restar x cantidad de horas a una fecha.
	 * 
	 * @param fecha
	 * @param numero
	 * @return
	 */
	public static Date restarHorasFecha(Date fecha, int numero) {
		Date nuevaFecha = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.HOUR_OF_DAY, numero);
		nuevaFecha = cal.getTime();
		return nuevaFecha;
	}

	/**
	 * Metodo que sirve para obtener horas fijas del sistemas Ejemplos (06:00:00) 0
	 * (12:00:00).
	 * 
	 * @return
	 */
	public static Date obtenerHoraFija() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}
}