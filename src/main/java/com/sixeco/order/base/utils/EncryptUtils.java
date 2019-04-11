package com.sixeco.order.base.utils;

import org.apache.logging.log4j.util.Strings;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptUtils {

	public static String encrypt(String content, String password) {
		try {
			if (Strings.isEmpty(content)) {
				return "";
			}
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			String str = Base64.getEncoder().encodeToString(result);
			return str;
		} catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			 e.printStackTrace();
		} catch (InvalidKeyException e) {
			 e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			 e.printStackTrace();
		} catch (BadPaddingException e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	
	public static String decrypt(String str, String password) {
		try {
			byte[] content = Base64.getDecoder().decode(str);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return new String(result,"UTF-8");
		} catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			 e.printStackTrace();
		} catch (InvalidKeyException e) {
			 e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			 e.printStackTrace();
		} catch (BadPaddingException e) {
			 e.printStackTrace();
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return "";
	}
	
}
