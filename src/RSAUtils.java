package com.hismart.pay.activity;

	import java.io.BufferedReader;  
	import java.io.IOException;  
	import java.io.InputStream;  
	import java.io.InputStreamReader;  
	import java.math.BigInteger;  
	import java.security.KeyFactory;  
	import java.security.KeyPair;  
	import java.security.KeyPairGenerator;  
	import java.security.NoSuchAlgorithmException;  
	import java.security.PrivateKey;  
	import java.security.PublicKey;  
	import java.security.interfaces.RSAPrivateKey;  
	import java.security.interfaces.RSAPublicKey;  
	import java.security.spec.InvalidKeySpecException;  
	import java.security.spec.PKCS8EncodedKeySpec;  
	import java.security.spec.RSAPublicKeySpec;  
	import java.security.spec.X509EncodedKeySpec;  
	import javax.crypto.Cipher;  
	  
	public final class RSAUtils  
	{  
	    private static String RSA = "RSA";  
	  
	    public static KeyPair generateRSAKeyPair()  
	    {  
	        return generateRSAKeyPair(1024);  
	    }  
	  
	    /** 
	     * ??æœº??Ÿæ?RSAå¯†é’¥å¯? 
	     *  
	     * @param keyLength 
	     *            å¯†é’¥?•¿åº¦ï?Œè?ƒå›´ï¼?512ï½?2048<br> 
	     *            ä¸??ˆ¬1024 
	     * @return 
	     */  
	    public static KeyPair generateRSAKeyPair(int keyLength)  
	    {  
	        try  
	        {  
	            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);  
	            kpg.initialize(keyLength);  
	            return kpg.genKeyPair();  
	        } catch (NoSuchAlgorithmException e)  
	        {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * ?”¨?…¬?’¥?? å?? <br> 
	     * æ¯æ¬¡?? å?†ç?„å?—è?‚æ•°ï¼Œä?èƒ½è¶…è?‡å?†é’¥??„é•¿åº¦å?¼å?å»11 
	     *  
	     * @param data 
	     *            ???? å?†æ•°?®??„byte?•°?® 
	     * @param pubKey 
	     *            ?…¬?’¥ 
	     * @return ?? å?†å?ç?„byte??‹æ•°?® 
	     */  
	    public static byte[] encryptData(byte[] data, PublicKey publicKey)  
	    {  
	        try  
	        {  
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	            // ç¼–ç?å?è®¾å®šç?–ç?æ–¹å¼å?Šå?†é’¥  
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	            // ä¼ å…¥ç¼–ç?æ•°?®å¹¶è?”å?ç?–ç?ç?“æ??  
	            return cipher.doFinal(data);  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * ?”¨ç§é’¥è§?å¯? 
	     *  
	     * @param encryptedData 
	     *            ç»è?‡encryptedData()?? å?†è?”å?ç?„byte?•°?® 
	     * @param privateKey 
	     *            ç§é’¥ 
	     * @return 
	     */  
	    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey)  
	    {  
	        try  
	        {  
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	            return cipher.doFinal(encryptedData);  
	        } catch (Exception e)  
	        {  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * ?šè?‡å…¬?’¥byte[](publicKey.getEncoded())å°†å…¬?’¥è¿˜å?Ÿï?Œé?‚ç”¨äºRSAç®—æ?? 
	     *  
	     * @param keyBytes 
	     * @return 
	     * @throws NoSuchAlgorithmException 
	     * @throws InvalidKeySpecException 
	     */  
	    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,  
	            InvalidKeySpecException  
	    {  
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
	        return publicKey;  
	    }  
	  
	    /** 
	     * ?šè?‡ç?é’¥byte[]å°†å…¬?’¥è¿˜å?Ÿï?Œé?‚ç”¨äºRSAç®—æ?? 
	     *  
	     * @param keyBytes 
	     * @return 
	     * @throws NoSuchAlgorithmException 
	     * @throws InvalidKeySpecException 
	     */  
	    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,  
	            InvalidKeySpecException  
	    {  
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
	        return privateKey;  
	    }  
	  
	    /** 
	     * ä½¿ç”¨N?e?¼è?˜å?Ÿå…¬?’¥ 
	     *  
	     * @param modulus 
	     * @param publicExponent 
	     * @return 
	     * @throws NoSuchAlgorithmException 
	     * @throws InvalidKeySpecException 
	     */  
	    public static PublicKey getPublicKey(String modulus, String publicExponent)  
	            throws NoSuchAlgorithmException, InvalidKeySpecException  
	    {  
	        BigInteger bigIntModulus = new BigInteger(modulus);  
	        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);  
	        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);  
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
	        return publicKey;  
	    }  
	  
	    /** 
	     * ä½¿ç”¨N?d?¼è?˜å?Ÿç?é’¥ 
	     *  
	     * @param modulus 
	     * @param privateExponent 
	     * @return 
	     * @throws NoSuchAlgorithmException 
	     * @throws InvalidKeySpecException 
	     */  
	    public static PrivateKey getPrivateKey(String modulus, String privateExponent)  
	            throws NoSuchAlgorithmException, InvalidKeySpecException  
	    {  
	        BigInteger bigIntModulus = new BigInteger(modulus);  
	        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);  
	        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);  
	        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
	        return privateKey;  
	    }  
	  
	    /** 
	     * ä»å?—ç¬¦ä¸²ä¸­?? è½½?…¬?’¥ 
	     *  
	     * @param publicKeyStr 
	     *            ?…¬?’¥?•°?®å­—ç¬¦ä¸? 
	     * @throws Exception 
	     *             ?? è½½?…¬?’¥?—¶äº§ç?Ÿç?„å?‚å¸¸ 
	     */  
	    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception  
	    {  
	        try  
	        {  
	            byte[] buffer = Base64Utils.decode(publicKeyStr);  
	            KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
	            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
	        } catch (NoSuchAlgorithmException e)  
	        {  
	            throw new Exception("?? æ­¤ç®—æ??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("?…¬?’¥??æ??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?…¬?’¥?•°?®ä¸ºç©º");  
	        }  
	    }  
	  
	    /** 
	     * ä»å?—ç¬¦ä¸²ä¸­?? è½½ç§é’¥<br> 
	     * ?? è½½?—¶ä½¿ç”¨??„æ˜¯PKCS8EncodedKeySpecï¼ˆPKCS#8ç¼–ç?ç?„Key??‡ä»¤ï¼‰ã?? 
	     *  
	     * @param privateKeyStr 
	     * @return 
	     * @throws Exception 
	     */  
	    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception  
	    {  
	        try  
	        {  
	            byte[] buffer = Base64Utils.decode(privateKeyStr);  
	            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
	            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
	            KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
	            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
	        } catch (NoSuchAlgorithmException e)  
	        {  
	            throw new Exception("?? æ­¤ç®—æ??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("ç§é’¥??æ??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("ç§é’¥?•°?®ä¸ºç©º");  
	        }  
	    }  
	  
	    /** 
	     * ä»æ?‡ä»¶ä¸­è?“å…¥æµä¸­?? è½½?…¬?’¥ 
	     *  
	     * @param in 
	     *            ?…¬?’¥è¾“å…¥æµ? 
	     * @throws Exception 
	     *             ?? è½½?…¬?’¥?—¶äº§ç?Ÿç?„å?‚å¸¸ 
	     */  
	    public static PublicKey loadPublicKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPublicKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("?…¬?’¥?•°?®æµè¯»??–é?™è¯¯");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?…¬?’¥è¾“å…¥æµä¸ºç©?");  
	        }  
	    }  
	  
	    /** 
	     * ä»æ?‡ä»¶ä¸­å? è½½ç§é’¥ 
	     *  
	     * @param keyFileName 
	     *            ç§é’¥??‡ä»¶??? 
	     * @return ?˜¯?¦??å?? 
	     * @throws Exception 
	     */  
	    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPrivateKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("ç§é’¥?•°?®è¯»å?–é?™è¯¯");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("ç§é’¥è¾“å…¥æµä¸ºç©?");  
	        }  
	    }  
	  
	    /** 
	     * è¯»å?–å?†é’¥ä¿¡æ¯ 
	     *  
	     * @param in 
	     * @return 
	     * @throws IOException 
	     */  
	    private static String readKey(InputStream in) throws IOException  
	    {  
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
	        String readLine = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((readLine = br.readLine()) != null)  
	        {  
	            if (readLine.charAt(0) == '-')  
	            {  
	                continue;  
	            } else  
	            {  
	                sb.append(readLine);  
	                sb.append('\r');  
	            }  
	        }  
	  
	        return sb.toString();  
	    }  
	  
	    /** 
	     * ??“å°?…¬?’¥ä¿¡æ¯ 
	     *  
	     * @param publicKey 
	     */  
	    public static void printPublicKeyInfo(PublicKey publicKey)  
	    {  
	        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;  
	        System.out.println("----------RSAPublicKey----------");  
	        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());  
	        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());  
	        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());  
	        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());  
	    }  
	  
	    public static void printPrivateKeyInfo(PrivateKey privateKey)  
	    {  
	        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;  
	        System.out.println("----------RSAPrivateKey ----------");  
	        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());  
	        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());  
	        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());  
	        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());  
	  
	    }  

}
