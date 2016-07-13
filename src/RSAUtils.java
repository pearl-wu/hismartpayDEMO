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
	     * 隨機生成RSA密鑰對 
	     *  
	     * @param keyLength 
	     *            密鑰長度，范圍：512～2048<br> 
	     *            一般1024 
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
	     * 用公鑰加密 <br> 
	     * 每次加密的字節數，不能超過密鑰的長度值減去11 
	     *  
	     * @param data 
	     *            需加密數據的byte數據 
	     * @param pubKey 
	     *            公鑰
	     * @return 加密后的byte型數據 
	     */  
	    public static byte[] encryptData(byte[] data, PublicKey publicKey)  
	    {  
	        try  
	        {  
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	            // 編碼前設定編碼方式及密鑰  
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	            // 傳入編碼數據並返回編碼結果  
	            return cipher.doFinal(data);  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
	    /** 
	     * 用私鑰解密 
	     *  
	     * @param encryptedData 
	     *            經過encryptedData()加密返回的byte數據 
	     * @param privateKey 
	     *            私鑰 
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
	     * 通過公鑰byte[](publicKey.getEncoded())將公鑰還原，適用于RSA算法 
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
	     * 通過私鑰byte[]將公鑰還原，適用于RSA算法 
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
	     * 使用N、e值還原公鑰 
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
	     * 使用N、d值還原私鑰 
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
	     * 以字符中加載公鑰 
	     *  
	     * @param publicKeyStr 
	     *            公鑰數據字符串 
	     * @throws Exception 
	     *             加載公鑰時產生的異常 
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
	            throw new Exception("無此算法");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("公鑰非法");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("公鑰數據為空");  
	        }  
	    }  
	  
	    /** 
	     * 以字符中加載私鑰<br> 
	     * 加載時使用的是PKCS8EncodedKeySpec（PKCS#8??的Key指令）。 
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
	            throw new Exception("無此算法");  
	        } catch (InvalidKeySpecException e)  
	        {  
	            throw new Exception("私鑰非法");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("私鑰數據為空");  
	        }  
	    }  
	  
	    /** 
	     * 以文件中輸入流中加載公鑰] 
	     *  
	     * @param in 
	     *            公鑰輸入流
	     * @throws Exception 
	     *             加載公鑰時產生的異常 
	     */  
	    public static PublicKey loadPublicKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPublicKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("公鑰數據流讀取錯誤");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("公鑰輸入流為空");  
	        }  
	    }  
	  
	    /** 
	     * 以文件中加載私鑰]
	     *  
	     * @param keyFileName 
	     *            私?文件名 
	     * @return 是否成功 
	     * @throws Exception 
	     */  
	    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
	    {  
	        try  
	        {  
	            return loadPrivateKey(readKey(in));  
	        } catch (IOException e)  
	        {  
	            throw new Exception("私??据?取??");  
	        } catch (NullPointerException e)  
	        {  
	            throw new Exception("私??入流?空");  
	        }  
	    }  
	  
	    /** 
	     * 讀取密鑰信息
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
	     * 打印公鑰信息
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
