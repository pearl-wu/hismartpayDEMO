package com.bais.hismart.pay.activity;

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
	     * ??乗惻??滓?審SA蟇�徴蟇? 
	     *  
	     * @param keyLength 
	     *            蟇�徴?柄蠎ｦ?瑚?�峩�?512�?2048<br> 
	     *            荳??握1024 
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
	     * ?畑?�?徴???? <br> 
	     * 豈乗ｬ｡???�?�?苓?よ焚�御?崎�雜�?�?�徴??�柄蠎ｦ?ｼ?丞悉11 
	     *  
	     * @param data 
	     *            ?????�焚?紺??�yte?焚?紺 
	     * @param pubKey 
	     *            ?�?徴 
	     * @return ???�?守?�yte??区焚?紺 
	     */  
	    public static byte[] encryptData(byte[] data, PublicKey publicKey)  
	    {  
	        try  
	        {  
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	            // 郛也?∝?崎ｮｾ螳夂?也?∵婿蠑丞?雁?�徴  
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	            // 莨蜈･郛也?∵焚?紺蟷ｶ?泌?樒?也?∫?捺??  
	            return cipher.doFinal(data);  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * ?畑遘�徴隗?蟇? 
	     *  
	     * @param encryptedData 
	     *            扈剰?㌃ncryptedData()???�?泌?樒?�yte?焚?紺 
	     * @param privateKey 
	     *            遘�徴 
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
	     * ?夊?��?徴byte[](publicKey.getEncoded())蟆��?徴霑伜?滂?碁?ら畑莠山SA邂玲?? 
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
	     * ?夊?�?�徴byte[]蟆��?徴霑伜?滂?碁?ら畑莠山SA邂玲?? 
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
	     * 菴ｿ逕ｨN?‘?ｼ?伜?溷�?徴 
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
	     * 菴ｿ逕ｨN?‥?ｼ?伜?溽?�徴 
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
	     * 莉主?礼ｬｦ荳ｲ荳ｭ??霓ｽ?�?徴 
	     *  
	     * @param publicKeyStr 
	     *            ?�?徴?焚?紺蟄礼ｬｦ荳? 
	     * @throws Exception 
	     *             ??霓ｽ?�?徴?慮莠ｧ?溽?�?ょｸｸ 
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
	            throw new Exception("??豁､邂玲??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("?�?徴??樊??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?�?徴?焚?紺荳ｺ遨ｺ");  
	        }  
	    }  
	  
	    /** 
	     * 莉主?礼ｬｦ荳ｲ荳ｭ??霓ｽ遘�徴<br> 
	     * ??霓ｽ?慮菴ｿ逕ｨ??�弍PKCS8EncodedKeySpec��KCS#8郛也?∫?Кey??�ｻ､�峨?? 
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
	            throw new Exception("??豁､邂玲??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("遘�徴??樊??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("遘�徴?焚?紺荳ｺ遨ｺ");  
	        }  
	    }  
	  
	    /** 
	     * 莉取?�ｻｶ荳ｭ?灘�豬∽ｸｭ??霓ｽ?�?徴 
	     *  
	     * @param in 
	     *            ?�?徴霎灘�豬? 
	     * @throws Exception 
	     *             ??霓ｽ?�?徴?慮莠ｧ?溽?�?ょｸｸ 
	     */  
	    public static PublicKey loadPublicKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPublicKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("?�?徴?焚?紺豬∬ｯｻ??夜?呵ｯｯ");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?�?徴霎灘�豬∽ｸｺ遨?");  
	        }  
	    }  
	  
	    /** 
	     * 莉取?�ｻｶ荳ｭ?霓ｽ遘�徴 
	     *  
	     * @param keyFileName 
	     *            遘�徴??�ｻｶ??? 
	     * @return ?弍?凄??仙?? 
	     * @throws Exception 
	     */  
	    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPrivateKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("遘�徴?焚?紺隸ｻ?夜?呵ｯｯ");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("遘�徴霎灘�豬∽ｸｺ遨?");  
	        }  
	    }  
	  
	    /** 
	     * 隸ｻ?門?�徴菫｡諱ｯ 
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
	     * ??灘魂?�?徴菫｡諱ｯ 
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
