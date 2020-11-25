package co.com.periferia.commons.dto.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class Pageable<T> extends AbstractResponseApi {
	
	private Integer pagina;
	private Integer cantidadDeRegistros;
	private Integer registrosPorPagina;
	private Long totalRegistros;
	private Integer totalPaginas;
	private List<T> registros;
}