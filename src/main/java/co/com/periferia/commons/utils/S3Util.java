package co.com.periferia.commons.utils;

import java.net.URL;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import co.com.periferia.commons.dto.service.InfoUrlAws;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3Util {
	
	
	private S3Util() {}
	
	/**
	 * Metodo utilitario para la generación de Url Prefirmada para carga de imagenes a un Bucket AWS S3
	 * @param objectKey
	 * @param bucketName
	 * @param region
	 * @return
	 */
	public static InfoUrlAws generatePresignedUrl(AWSCredentialsProvider credenciales,
			String objectKey, String bucketName, String region) {
	     InfoUrlAws infoUrlAws = new InfoUrlAws();
         AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                 .withRegion(region)
                 .withCredentials(credenciales)
                 .build();

         // Set the pre-signed URL to expire after FIVE MINUTES
         
         java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 5;
            expiration.setTime(expTimeMillis);
            
         log.info("Fecha expiracion -->> " + expiration.getTime());

         
         // Generate the pre-signed URL.
         log.info("Generating pre-signed URL with bucketname ----> "+bucketName+" and objectKey ---> "+objectKey);
         GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                 .withMethod(HttpMethod.PUT)
                 .withExpiration(expiration)
                 .withContentType("image/svg+xml");
         
         URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
         log.info("Url --> " + url.toString());
         infoUrlAws.setUrl(url);
         infoUrlAws.setFechaExpiracion(expiration.toString());
         log.info("Url en el DTO ---> "+infoUrlAws.getUrl());
         return infoUrlAws;
    }
	
	/**
	 * Metodo utilitario para la generación de Url Prefirmada para carga de imagenes a un Bucket AWS S3
	 * @param objectKey
	 * @param bucketName
	 * @param region
	 * @return
	 */
	public static InfoUrlAws generatePresignedUrlLocal(AWSStaticCredentialsProvider credenciales,
			String objectKey, String bucketName, String region) {
	     InfoUrlAws infoUrlAws = new InfoUrlAws();
         AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                 .withRegion(region)
                 .withCredentials(credenciales)
                 .build();

         // Set the pre-signed URL to expire after FIVE MINUTES
         
         java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 5;
            expiration.setTime(expTimeMillis);
            
         log.info("Fecha expiracion -->> " + expiration.getTime());

         
         // Generate the pre-signed URL.
         log.info("Generating pre-signed URL with bucketname ----> "+bucketName+" and objectKey ---> "+objectKey);
         GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                 .withMethod(HttpMethod.PUT)
                 .withExpiration(expiration);
         URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
         log.info("Url --> " + url.toString());
         infoUrlAws.setUrl(url);
         infoUrlAws.setFechaExpiracion(expiration.toString());
         log.info("Url en el DTO ---> "+infoUrlAws.getUrl());
         return infoUrlAws;
    }
}
