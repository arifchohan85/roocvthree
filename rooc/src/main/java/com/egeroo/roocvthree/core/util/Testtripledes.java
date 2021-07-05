package com.egeroo.roocvthree.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Testtripledes {
	
	//public static void main(String[] args) {		
	public void testencryptbca3des() {
		try {
		String hasil = Testtripledes.cryptBC("Bca123!", "123456789013245678901234");
		System.out.println("Hasil : " + hasil);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String cryptBC(String data, String key) throws Exception{
	   // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	   byte[] input = data.getBytes();
	   byte[] keyBytes = key.getBytes() ;   
	   SecretKeySpec skey = new SecretKeySpec(keyBytes, "DESede");
	   Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");

	   if(input.length % 8 != 0){ 
	       byte[] padded = new byte[input.length + 8 - (input.length % 8)];
	       System.arraycopy(input, 0, padded, 0, input.length);
	       input = padded;
	   }
	   System.out.println("input : " + new String(input));
	   cipher.init(Cipher.ENCRYPT_MODE, skey);
	   byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
	   int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
	   ctLength += cipher.doFinal(cipherText, ctLength);

	   return new String(Testtripledes.encodeHexString(cipherText));
	}	
	 
	public static String encodeHexString(byte[] byteArray) {
	   StringBuffer hexStringBuffer = new StringBuffer();
	   for (int i = 0; i < byteArray.length; i++) {
	       hexStringBuffer.append(byteToHex(byteArray[i]));
	   }
	   return hexStringBuffer.toString();
	}
	
	public static String byteToHex(byte num) {
	   char[] hexDigits = new char[2];
	   hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
	   hexDigits[1] = Character.forDigit((num & 0xF), 16);
	   return new String(hexDigits);
	}	

}
