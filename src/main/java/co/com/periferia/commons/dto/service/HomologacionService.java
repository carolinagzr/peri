package co.com.periferia.commons.dto.service;

import java.util.Optional;

import co.com.periferia.commons.dto.api.HomologacionCodigoRespuesta;

/**
 * Homologa el codigo de error de respuesta
 * 
 * @author Periferia IT
 *
 */
public interface HomologacionService {
	/**
	 * Homologa el codigo de error de respuesta que se debe entregar al legado especificado
	 * 
	 * @param codigoOrigen	codigo de respuesta a homologar
	 * @param legadoDestino
	 * @return	{@link Optional} con el valor de la homologacion o {@code Optional.empy()} si no se encuentra
	 * 			la homologacion
	 */
	Optional<HomologacionCodigoRespuesta> homologarCodigoRespuesta(String codigoOrigen, String legadoOrigen, String legadoDestino);
	
	/**
	 * Homologa el dato origen
	 * 
	 * @param datoOrigen	dato a homologar
	 * @param legadoOrigen	legado origen
	 * @param legadoDestino	legado destino
	 * @param referencia	tipo de homologacion
	 * @return	{@link Optional} con el valor de la homologacion o {@code Optional.empy()} si no se encuentra
	 * 			la homologacion
	 */
	Optional<String> homologarDato(String datoOrigen, String legadoOrigen, String legadoDestino, int referencia);
}
