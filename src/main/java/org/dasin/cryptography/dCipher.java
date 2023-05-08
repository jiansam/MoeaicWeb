package org.dasin.cryptography;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.*;

public class dCipher {
	private static final String ALGORITHM = "DESede";
	private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";

	private static String dasin_key = "The key shoud be 24 Byte";
	private static String dasin_iv = "dasin-iv";
	
	private static final SecretKeySpec secretKey = new SecretKeySpec(dasin_key.getBytes(), ALGORITHM);
	private static final IvParameterSpec iv = new IvParameterSpec(dasin_iv.getBytes());
	
	private static final Base64.Encoder encoder = Base64.getMimeEncoder();
	private static final Base64.Decoder decoder = Base64.getMimeDecoder();
	
	public static String encrypt(String inString){
		try{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
	
			return new String(encoder.encode(cipher.doFinal(inString.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public static String decrypt(String inString){
		inString = inString.replaceAll(" ", "+");
		
		try{
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	
			return new String(cipher.doFinal(decoder.decode(inString)), StandardCharsets.UTF_8);
		}catch(IllegalArgumentException iae) {
			System.out.println("Decrypting " + inString + " failed.");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}