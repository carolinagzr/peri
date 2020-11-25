package co.com.periferia.commons.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Informacion de la respuesta del legado
 * 
 * @author Periferia IT Group
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalStatus {
	/**
	 * Codigo de respuesta del legado
	 */
	private String serverStatusCode;
	/**
	 * Severidad
	 */
	private String severity;
	/**
	 * Descripcion de la respuesta
	 */
	private String statusDesc;
}