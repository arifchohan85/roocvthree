package com.egeroo.roocvthree.core.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.error.CoreException;

public class Util {
	
	
    
    private static final String UNICODE_FORMAT = "UTF8";
    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String CIPHER_ALG = "DESede/ECB/Nopadding"; //assuming no padding
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private byte[] arrayBytes;
    private String myEncryptionKey;
    private SecretKey key;
    
    public KeyGenerator keygenerator;
    public SecretKey myDesKey;
    Cipher c;
    public SecretKey ky;
    
    
    
    public byte[] doEncryption(String s,String encryptionKey) throws Exception
    {
    	  myEncryptionKey = encryptionKey;
    	  arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
    	  ks = new DESedeKeySpec(arrayBytes);
    	  skf = SecretKeyFactory.getInstance("DESede");
    	  myDesKey = skf.generateSecret(ks);//keygenerator.generateKey();
    	  // Create the cipher
    	  c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    	  // Initialize the cipher for encryption
    	  c.init(Cipher.ENCRYPT_MODE, myDesKey);
    	  //sensitive information
    	  byte[] text = s.getBytes();
    	  // Encrypt the text
    	  byte[] textEncrypted = c.doFinal(text);
      return(textEncrypted);

    }
    
    public String doDecryption(byte[] s)throws Exception
    {
    
        // Initialize the same cipher for decryption
        c.init(Cipher.DECRYPT_MODE, myDesKey);

     
        // Decrypt the text
        byte[] textDecrypted = c.doFinal(s);
      
    
      return(new String(textDecrypted));
    }
    
    public String doDecryptiononly(byte[] s,String encryptionKey)throws Exception
    {
    	myEncryptionKey = encryptionKey;
  	  	arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
  	  	ks = new DESedeKeySpec(arrayBytes);
  	  	skf = SecretKeyFactory.getInstance("DESede");
  	  	myDesKey = skf.generateSecret(ks);//keygenerator.generateKey();
  	  	// Create the cipher
  	  	c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    	
        // Initialize the same cipher for decryption
        c.init(Cipher.DECRYPT_MODE, myDesKey);

     
        // Decrypt the text
        byte[] textDecrypted = c.doFinal(s);
      
    
      return(new String(textDecrypted));
    }
    
    public String encrypt3Des_base64(String unencryptedString)  throws Exception{
        String encryptedString = null;
        myEncryptionKey = "123456789013245678901234";
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);

        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//CIPHER_ALG
        key = skf.generateSecret(ks);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    
    public String decrypt3Des_base64(String encryptedString)  throws Exception{
    	/*myEncryptionKey = "123456789013245678901234";
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);

        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        key = skf.generateSecret(ks);*/
    	
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
    
    public static String bytesToHex(final byte[] bytes) {
        final StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                buf.append("0");
            }
            buf.append(hex);
        }
        return buf.toString();
    }

    public static byte[] hexToBytes(final String hex) {
        final byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
    
    public byte[] encTripleDes (String txt, byte [] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException{
        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        this.ky = keyfactory.generateSecret(keySpec);

        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, ky);
        return cipher.doFinal(txt.getBytes("UTF-8"));

    }
    
    public byte[] uncTripleDes (byte [] encryptedTextBytes, byte [] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException, DecoderException{
    	//byte [] bytes = Hex.decodeHex(encryptedTextBytes.toString());
    	
    	/*DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        SecretKey ky = keyfactory.generateSecret(keySpec);*/

        //cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, ky);
        return cipher.doFinal(encryptedTextBytes);

    }


    /*public static void main(String args []) throws Exception
    {
        String url ="https://api.rooc.egeroo.com/quartzdemo/api/schedulestop?jobName=Reminder+Polis+-+Lapse+-+Try2";
        String jobname ="Reminder+Polis+-+Lapse+-+Try2";
        System.out.print("Raw URL : " + url);
        System.out.print("Clean URL : " + URLDecoder.decode(url));
        
        System.out.print("Raw name : " + jobname);
        System.out.print("Clean name : " + URLDecoder.decode(jobname));

    }*/
    
    public String validateUri(String link)
	{
		URL url = null;
		try {
			   url = new URL(link);
			   System.out.println(link);
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}

			URI uri = null;
			try{
			   uri = new URI(url.toString());
			   System.out.println("uri :" + uri);
			} catch(URISyntaxException e) {
			   try {
			        uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
			                      url.getPort(), url.getPath(), url.getQuery(), 
			                      url.getRef());
			   } catch(URISyntaxException e1) {
			        e1.printStackTrace();
			   }
			}
			
			try {
			   url = uri.toURL();
			   System.out.println("uri to url :" + url.toString());
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}
		return url.toString();
	}
    
    public String parseWhereclause(String field,String condition,String where1,String where2)
    {
    	String returnData ="";
    	
    	boolean isString = false;
    	
    	isString = this.isInteger(where1);
    	
    	if(condition.equals("contains"))
    	{
    		//field like '%whereclause%'
    		returnData =field + " like " + " '%" + where1 +"%' ";
    	}
    	else if(condition.equals("notContains"))
    	{
    		//field not like '%whereclause%'
    		returnData =field + " not like " + " '%" + where1 +"%' ";
    	}
    	else if(condition.equals("startsWith"))
    	{
    		//field like '%whereclause'
    		returnData =field + " like " + " '%" + where1 +"' ";
    	}
    	else if(condition.equals("endsWith"))
    	{
    		//field like 'whereclause%'
    		returnData =field + " like " + " '" + where1 +"%' ";
    	}
    	else if(condition.equals("equal"))
    	{
    		//field = 10
    		if(isString)
    		{
    			returnData =field + " = '" + where1 +"' ";
    		}
    		else
    		{
    			returnData =field + " = " + where1 +" ";
    		}
    		
    	}
    	else if(condition.equals("notEqual"))
    	{
    		//field <> 10
    		//returnData =field + " <> " + where1 +" ";
    		if(isString)
    		{
    			returnData =field + " <> '" + where1 +"' ";
    		}
    		else
    		{
    			returnData =field + " <> " + where1 +" ";
    		}
    	}
    	else if(condition.equals("greaterThan"))
    	{
    		//field > 10
    		returnData =field + " > " + where1 +" ";
    	}
    	else if(condition.equals("greaterThanOrEqual"))
    	{
    		//field >= 10
    		returnData =field + " >= " + where1 +" ";
    	}
    	else if(condition.equals("lessThan"))
    	{
    		//field < 10
    		returnData =field + " < " + where1 +" ";
    	}
    	else if(condition.equals("lessThanOrEqual"))
    	{
    		//field <= 10
    		returnData =field + " <= " + where1 +" ";
    	}
    	else if(condition.equals("between"))
    	{
    		//field between 10 and 15
    		returnData =field + " between '" + where1 +"' and '" + where2 + "'";
    	}
    	else
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Unexpected Comparator");
    	}
    	
		return returnData;
    	
    }
    
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        
        /*
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        */
        
        
        /*for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        } */
        
        /*
        try {

            Float.parseFloat(str);
            //return true;

        } catch (NumberFormatException e) {
            return false;
        }
        
        try {

            Double.parseDouble(str);
            //return true;

        } catch (NumberFormatException e) {
            return false;
        }
        
        try {

            Integer.parseInt(str);
            //return true;

        } catch (NumberFormatException e) {
            return false;
        }
        */
        
        
        
        return str.chars().allMatch(Character::isDigit);
         
        
        //return true;
    }
    
    public static boolean isNumeric(String str) {
    	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

}
