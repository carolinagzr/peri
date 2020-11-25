package co.com.periferia.commons.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Estado de la respuesta
 * 
 * @author Periferia IT
 *
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {
	/**
	 * Codigo de respuesta
	 */
	private String statusCode;
	/**
	 * Severidad
	 */
	private String severity;
	/**
	 * Mensaje de respuesta
	 */
	private String statusDesc;
	/**
	 * Informacion adicional
	 */
	private AdditionalStatus additionalStatus;
}