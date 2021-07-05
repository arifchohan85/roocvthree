package com.egeroo.roocvthree.core.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;*/
import org.bouncycastle.util.encoders.Hex;

public class TripleDes {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/*public static void main(String[] args) {
		try {
		
		String hexKey = "0F42875CD95A5D11";
		String plainText = "Ballon On Sky";
		byte[] key = Hex.decode(hexKey);
		
		System.out.println("Plain Key: "+hexKey);
		System.out.println("Plain Text: " + plainText);
		
		byte[] encryptedData = encrypt(plainText, key);
		String encryptedString = new String(encryptedData);
		String encryptedHexString = bytesToHex(encryptedData);
		
		System.out.println("Encrypted data in Bytes: "+encryptedString);
		System.out.println("Encrypted data in Hex: "+encryptedHexString);
		
		String dencryptedText = decrypt(encryptedHexString, key);
		System.out.println("Dencrypted Text :"+dencryptedText);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}*/

	public static String encrypt(String value, String keyString, boolean isPlainKey) throws Exception {
		if (isPlainKey) {
			return bytesToHex(encrypt(value, keyString.getBytes()));
		} else {
			byte[] key = Hex.decode(keyString);
			return bytesToHex(encrypt(value, key));
		}
	}

	public static String decrypt(String value, String keyString, boolean isPlainKey) throws Exception {
		if (isPlainKey) {
			return decrypt(value, keyString.getBytes());
		} else {
			byte[] key = Hex.decode(keyString);
			return decrypt(value, key);
		}
	}

	public static byte[] encrypt(String value, byte[] keyBytes) throws Exception {
		String toEncrypt = new String(value);
		
		byte[] input = toEncrypt.getBytes();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		
		Cipher cipher = null;
		if(value.length() % 8 == 0){
			cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");
		}else{
			cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding", "BC");
		}
		
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		return cipherText;
	}

	public static String decrypt(String value, byte[] keyBytes) throws Exception {
		byte[] input = hexStringToByteArray(value);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] plainText = new byte[cipher.getOutputSize(input.length)];
		int ptLength = cipher.update(input, 0, input.length, plainText, 0);
		ptLength += cipher.doFinal(plainText, ptLength);
		
		return new String(plainText);
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

}