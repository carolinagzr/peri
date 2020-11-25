package co.com.periferia.commons.utils;

import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.AWSSecretsManagerException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.periferia.commons.dto.api.PeriferiaException;
import co.com.periferia.commons.enums.PeriferiaConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AwsSecretsManagerUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getSecret(String secretName, String awsRegion, AWSCredentialsProvider credenciales, String profile){
		log.info("Obteniendo secreto");
		
		try {
			if(profile.equals(PeriferiaConstants.PROFILE_DEFAULT)) {
				 return new ObjectMapper().readValue((
						 AWSSecretsManagerClientBuilder.standard()
						 .withRegion(awsRegion)
						 .build().getSecretValue(new GetSecretValueRequest().withSecretId(secretName))).getSecretString(), Map.class);
			} else{
				 return new ObjectMapper().readValue((
						 AWSSecretsManagerClientBuilder.standard()
						 .withRegion(awsRegion)
						 .withCredentials(credenciales)
						 .build().getSecretValue(new GetSecretValueRequest().withSecretId(secretName))).getSecretString(), Map.class);
			}
		}  catch (JsonProcessingException  e) {
			log.error("Error al procesar json: "+ e.getMessage() , e);
			return null;
		}catch (AWSSecretsManagerException seex) {
			log.error("Error al obtener secreto: "+ seex.getMessage() , seex);
			throw new PeriferiaException("01", "Error");
		} 
		catch(Exception ex) {
			log.error("Error inesperado al obtener secreto: "+ ex.getMessage() , ex);
			return null;
		}
	}
	
	private AwsSecretsManagerUtil() {}
}
