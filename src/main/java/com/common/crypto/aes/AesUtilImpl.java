package com.common.crypto.aes;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesUtilImpl{

	public static String encrypt(String content, String password) throws Exception {
		// 创建AES的Key生产者
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 利用用户密码作为随机数初始化出 128位的key生产者
		// 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
		kgen.init(128, new SecureRandom(password.getBytes()));
		// 根据用户密码，生成一个密钥
		SecretKey secretKey = kgen.generateKey();
		// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null
		byte[] enCodeFormat = secretKey.getEncoded();
		// 转换为AES专用密钥
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("AES");
		// 初始化为加密模式的密码器
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 加密
		return  Base64.getEncoder().encodeToString(
				cipher.doFinal(content.getBytes("UTF-8")));
		
	}

	public static String decrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
     	return new String(cipher.doFinal(Base64.getDecoder().decode(content)),"UTF-8"); 
	}

	public static void main(String[] args) throws Exception{
		String content = "测试a1狗";
        String password = "1234";//567890abcdef";
        System.out.println("加密之前：" + content);

        // 加密
        String encrypt =  encrypt(content, password);
        System.out.println("加密后的内容：" + encrypt);
        
        // 解密
        String decrypt = decrypt(encrypt, password);
        System.out.println("解密后的内容：" +  decrypt); 
        System.out.println("see see:\n"+Arrays.toString(Base64.getDecoder().decode(encrypt)));
        System.out.println(new String(Base64.getDecoder().decode(encrypt)));
	}

}
