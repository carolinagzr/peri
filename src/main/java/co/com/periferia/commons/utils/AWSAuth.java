package co.com.periferia.commons.utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.STSAssumeRoleWithWebIdentitySessionCredentialsProvider;

public class AWSAuth {
	
	private AWSAuth(){}
	
	/**
	 * Metodo para la autorizacion por IAM en eks mediande IRSA
	 * 
	 * @param awsIamrole   Arn del Rol IAM que asumira la funcion provisto por la
	 *                     cuenta de servicio en K8S
	 * @param awsIrsaToken Ruta del archivo que continene el token de autorizacion
	 * @return AmazonCloudWatch Instancia de CloudWatch inicializada
	 */
	public static AWSCredentialsProvider auth(String awsIamrole, String awsIrsaToken) {
		return new STSAssumeRoleWithWebIdentitySessionCredentialsProvider.Builder(
				awsIamrole, "cwuekssn", awsIrsaToken).build();
	}
	

	/**
	 * Metodo para la autorizacion y autenticacion en AWS mediante credenciales
	 * temporales para uso en las maquinas locales de desarrollo
	 * 
	 * @param accessKeyId     Llave de acceso de AWS
	 * @param secretAccessKey Llave secreta de acceso de AWS
	 * @param sessionToken    Token de session de AWS
	 * @return AmazonCloudWatch Instancia de CloudWatch inicializada
	 */
	public static AWSStaticCredentialsProvider authLocal(String accessKeyId, String secretAccessKey, String sessionToken) {
		return new AWSStaticCredentialsProvider(new BasicSessionCredentials(accessKeyId, secretAccessKey,
				sessionToken));
	}
}
