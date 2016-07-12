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
	     * ??�机??��?�RSA密钥�? 
	     *  
	     * @param keyLength 
	     *            密钥?��度�?��?�围�?512�?2048<br> 
	     *            �??��1024 
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
	     * ?��?��?��??��?? <br> 
	     * 每次??��?��?��?��?�数，�?�能超�?��?�钥??�长度�?��?�去11 
	     *  
	     * @param data 
	     *            ????��?�数?��??�byte?��?�� 
	     * @param pubKey 
	     *            ?��?�� 
	     * @return ??��?��?��?�byte??�数?�� 
	     */  
	    public static byte[] encryptData(byte[] data, PublicKey publicKey)  
	    {  
	        try  
	        {  
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	            // 编�?��?�设定�?��?�方式�?��?�钥  
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	            // 传入编�?�数?��并�?��?��?��?��?��??  
	            return cipher.doFinal(data);  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * ?��私钥�?�? 
	     *  
	     * @param encryptedData 
	     *            经�?�encryptedData()??��?��?��?��?�byte?��?�� 
	     * @param privateKey 
	     *            私钥 
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
	     * ?��?�公?��byte[](publicKey.getEncoded())将公?��还�?��?��?�用于RSA算�?? 
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
	     * ?��?��?�钥byte[]将公?��还�?��?��?�用于RSA算�?? 
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
	     * 使用N?�e?��?��?�公?�� 
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
	     * 使用N?�d?��?��?��?�钥 
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
	     * 从�?�符串中??�载?��?�� 
	     *  
	     * @param publicKeyStr 
	     *            ?��?��?��?��字符�? 
	     * @throws Exception 
	     *             ??�载?��?��?��产�?��?��?�常 
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
	            throw new Exception("??�此算�??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("?��?��??��??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?��?��?��?��为空");  
	        }  
	    }  
	  
	    /** 
	     * 从�?�符串中??�载私钥<br> 
	     * ??�载?��使用??�是PKCS8EncodedKeySpec（PKCS#8编�?��?�Key??�令）�?? 
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
	            throw new Exception("??�此算�??");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("私钥??��??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("私钥?��?��为空");  
	        }  
	    }  
	  
	    /** 
	     * 从�?�件中�?�入流中??�载?��?�� 
	     *  
	     * @param in 
	     *            ?��?��输入�? 
	     * @throws Exception 
	     *             ??�载?��?��?��产�?��?��?�常 
	     */  
	    public static PublicKey loadPublicKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPublicKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("?��?��?��?��流读??��?�误");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("?��?��输入流为�?");  
	        }  
	    }  
	  
	    /** 
	     * 从�?�件中�?�载私钥 
	     *  
	     * @param keyFileName 
	     *            私钥??�件??? 
	     * @return ?��?��??��?? 
	     * @throws Exception 
	     */  
	    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPrivateKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("私钥?��?��读�?��?�误");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("私钥输入流为�?");  
	        }  
	    }  
	  
	    /** 
	     * 读�?��?�钥信息 
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
	     * ??�印?��?��信息 
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
