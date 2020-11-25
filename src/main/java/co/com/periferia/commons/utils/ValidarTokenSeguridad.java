package co.com.periferia.commons.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import co.com.periferia.commons.dto.service.TokenSecurityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase utilitaria que desencritpa el token y devuelve la informacion.
 * @author jeissoncastillo
 *
 */
@Slf4j
public class ValidarTokenSeguridad {
	
	private ValidarTokenSeguridad() {}
	
	public static TokenSecurityDto desencriptarToken(String token, String privateKey) throws Exception {
		log.info("Entra a desencriptarToken y convierte el resultado en TokenSecurityDto");
		String resultadoDecrypt = SecurityUtil.decrypt(SecurityUtil.getPrivateKey(privateKey),
			token);
		String[] datos = resultadoDecrypt.split("\\|");
		
		Calendar fechaDecifrada = getTimestampToCalendar(datos[0]);
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
	
		log.info("fecha actual ----> "+fechaActual.getTime());
		log.info("Zona horaria de la fecha actual ---> "+fechaActual.getTimeZone().getDisplayName());
		
		log.info("comparacion fechas -----> "+fechaDecifrada.compareTo(fechaActual));
		if(fechaDecifrada.compareTo(fechaActual) >= 0) {
			return stringToDto(fechaDecifrada.getTime().toString(), datos);
		}else {
			return stringToDto(fechaDecifrada.getTime().toString(), datos);

		}
	}
	
	private static TokenSecurityDto stringToDto(String timestamp, String[] datos) {
		TokenSecurityDto tokenSecurityDto = new TokenSecurityDto();
		tokenSecurityDto.setTimestamp(timestamp);
		tokenSecurityDto.setUser(datos[1]);
		tokenSecurityDto.setRole(datos[2]);
		tokenSecurityDto.setEmail(datos[3]);
		return tokenSecurityDto;
	}
	
	private static Calendar getTimestampToCalendar(String timestamp) {
		String timestampStr = timestamp;
		long tsLong = Long.parseLong(timestampStr) * 1000;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(tsLong));
		c.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		log.info("fecha calendar -----> "+c.getTime());
		
		log.info("fecha timestamp -----> "+tsLong);
		
		return c;
	}

}
