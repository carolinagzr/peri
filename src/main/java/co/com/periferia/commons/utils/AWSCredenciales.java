package co.com.periferia.commons.utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;

public class AWSCredenciales {
	
	private AWSCredenciales() {}
	
	public static AWSCredentialsProvider getCredentials(String iamRole, String irsaToken) {
		return AWSAuth.auth(iamRole, irsaToken);
	}
	
	public static AWSStaticCredentialsProvider getCredentialsLocal(String accessKeyId, String secretAccessKey, String sessionToken) {
		return AWSAuth.authLocal(accessKeyId, secretAccessKey, sessionToken);
	}

}
