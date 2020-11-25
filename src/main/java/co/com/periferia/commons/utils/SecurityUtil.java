package co.com.periferia.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
	
	private static final String ALGORITHM = "RSA";
	
	private SecurityUtil() {}
	
	private static String getKey(String filename) throws IOException{
		String strKeyPEM = "";
		//FileReader r = new FileReader(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getClass().getResourceAsStream(filename))
				);
		try {
			String line;
			while ((line = br.readLine()) != null) {
				strKeyPEM += line + "\n";
			}
		}finally {
			br.close();
		}
		return strKeyPEM;
	}
	
	public static RSAPrivateKey getPrivateKey(String filename){
		try {
			String privateKeyPEM = getKey(filename);
			return getPrivateKeyFromString(privateKeyPEM);
		} catch (IOException | GeneralSecurityException e) {
			log.warn("getPrivateKeyException --->"+e.toString());
		}
		return null;
	}
	
	public static RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException{
		log.info("Entra a obtener la clave privada");
		String privateKeyPEM = key;
		privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----", "");
		privateKeyPEM = privateKeyPEM.replace("\n", "");
		privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----", "");
		
		byte[] encoded = Base64.getDecoder().decode(privateKeyPEM.getBytes());
		
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(new ASN1Integer(0));
		ASN1EncodableVector v2 = new ASN1EncodableVector();
		v2.add(new ASN1ObjectIdentifier(PKCSObjectIdentifiers.rsaEncryption.getId()));
		v2.add(DERNull.INSTANCE);
		v.add(new DERSequence(v2));
		v.add(new DEROctetString(encoded));
		ASN1Sequence seq = new DERSequence(v);
		byte[] privKey = seq.getEncoded("DER");
		 
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKey);
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
		return (RSAPrivateKey) kf.generatePrivate(keySpec);
	}
	
	public static String decrypt(PrivateKey key, String ciphertext) {
		try {
			log.info("Inicia la desencripcion del mensaje y devuelve el string");
			byte[] byteCipherText = Base64.getDecoder().decode(ciphertext.getBytes());
			
			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
			OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", 
					new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);
			cipher.init(Cipher.DECRYPT_MODE, key, oaepParams);
			byte[] finalText = cipher.doFinal(byteCipherText);
			return new String(finalText, StandardCharsets.ISO_8859_1);
		} catch (NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|
				IllegalBlockSizeException|BadPaddingException|InvalidAlgorithmParameterException e) {
			log.warn("Excepcion --->"+e.toString());
		} 
		
		return "";
	}
}
