package co.com.periferia.commons.utils;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SQSUtil {
	
	
	
	private SQSUtil() {}
	/**
	 * Metodo utilitario para el envio de mensajes a un cola
	 * @param queueUrl
	 * @param messageBoddy
	 */
	public static void sendMessage(AWSCredentialsProvider credenciales, String queueUrl, String region, Object object) {
		try {
		log.info("QueueUrl --> "+queueUrl+" / Region ---> "+region);
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(region)
				.withCredentials(credenciales)
				.build();
	
		SendMessageRequest sendMsgRequest = new SendMessageRequest().withQueueUrl(queueUrl).withDelaySeconds(5);
		sendMsgRequest.withMessageBody(JsonUtil.object2Json(object));
		sqs.sendMessage(sendMsgRequest);
		log.info("Mensaje enviado a la cola correctamente");
		}catch(AmazonSQSException ex) {
			log.warn("Error en AWS AmazonSQSException -->"+ex.toString());
		}catch(AmazonServiceException ex) {
			log.warn("Error en AWS AmazonServiceException---> "+ex.toString());
		}catch(AmazonClientException ex) {
			log.warn("Error en AWS AmazonClientException ---> "+ex.toString());
		}
	}
	
	/**
	 * Metodo utilitario para recibir los mensajes de una cola
	 */
	public static List<Message> receiveMessage(AWSCredentialsProvider credenciales, String queueUrl, String region) {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(region)
				.withCredentials(credenciales)
				.build();
		ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest()
				.withQueueUrl(queueUrl)
				.withWaitTimeSeconds(15)
				.withMaxNumberOfMessages(10);
		return sqs.receiveMessage(receiveRequest).getMessages();
	}
	
	/**
	 * Metodo Utilitario para eliminar los mensajes de una cola
	 */
	public static void deleteMessage(AWSCredentialsProvider credenciales, String queueUrl, String region, Message message) {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(region)
				.withCredentials(credenciales)
				.build();
		sqs.deleteMessage(queueUrl, message.getReceiptHandle());
	}
	
	/**
	 * Metodo utilitario para el envio de mensajes a un cola
	 * @param queueUrl
	 * @param messageBoddy
	 */
	public static void sendMessageLocal(AWSStaticCredentialsProvider credenciales, 
			String queueUrl, Object object, String region) {
		try {
			log.info("QueueUrl --> "+queueUrl+" / Region ---> "+region);
			AmazonSQS sqs = AmazonSQSClientBuilder.standard()
					.withRegion(region)
					.withCredentials(credenciales)
					.build();
		
			SendMessageRequest sendMsgRequest = new SendMessageRequest().withQueueUrl(queueUrl).withDelaySeconds(5);
			sendMsgRequest.withMessageBody(JsonUtil.object2Json(object));
			sqs.sendMessage(sendMsgRequest);
			log.info("Mensaje enviado a la cola correctamente");
		}catch(AmazonSQSException ex) {
			log.warn("Error en AWS AmazonSQSException -->"+ex.toString());
		}catch(AmazonServiceException ex) {
			log.warn("Error en AWS AmazonServiceException---> "+ex.toString());
		}catch(AmazonClientException ex) {
			log.warn("Error en AWS AmazonClientException ---> "+ex.toString());
		}

	}
	
	/**
	 * Metodo utilitario para recibir los mensajes de una cola
	 */
	public static List<Message> receiveMessageLocal(AWSStaticCredentialsProvider credenciales, 
			String queueUrl, String region) {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(region)
				.withCredentials(credenciales)
				.build();
		ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest()
				.withQueueUrl(queueUrl)
				.withWaitTimeSeconds(15)
				.withMaxNumberOfMessages(10);
		return sqs.receiveMessage(receiveRequest).getMessages();
	}
	
	/**
	 * Metodo Utilitario para eliminar los mensajes de una cola
	 */
	public static void deleteMessageLocal(AWSStaticCredentialsProvider credenciales,
			String queueUrl, Message message, String region) {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(region)
				.withCredentials(credenciales)
				.build();
		sqs.deleteMessage(queueUrl, message.getReceiptHandle());
	}

}
