package co.com.periferia.commons.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Cabecera del mensaje de respuesta
 * 
 * @author Periferia IT
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgRsHdr {
	/**
	 * Estado
	 */
	private Status status;
}