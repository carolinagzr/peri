package co.com.periferia.commons.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Error
 * 
 * @author Periferia IT
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorCode {
	/**
	 * Codigo http de respuesta
	 */
	private String httpCode;
	/**
	 * Mensaje http de respuesta
	 */
	private String httpMessage;
	/**
	 * Informacion adicional
	 */
	private String moreInformation;
}