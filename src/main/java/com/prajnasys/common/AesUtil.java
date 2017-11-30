package com.prajnasys.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static final String BASE_PASSWORD = "DA13AD433859DA2";

    public static void main(String[] args) {
        String content = "{\"name\":\"张三\",\"phone\":\"13012345678\",\"idCard\":\"620102197211016519\",\"flag\":\"0\"}";
        String password = "WfWP5XWtdGAu";
        try {
            System.out.println("加密前: " + content);
            String encodeStr = encryptStr(content, password);
            System.out.println("加密后: " + encodeStr);
            String decodeStr = decryptStr(encodeStr, password);
            System.out.println("解密后: " + decodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encryptStr(String content, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(getKey(password), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器  
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
        byte[] result = cipher.doFinal(byteContent);
        return parseByte2HexStr(result); // 加密  
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decryptStr(String content, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] byteContent = parseHexStr2Byte(content);
        SecretKeySpec key = new SecretKeySpec(getKey(password), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器  
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
        byte[] result = cipher.doFinal(byteContent);
        return new String(result, "utf-8"); // 加密  
    }

    private static byte[] getKey(String password) {
        password += BASE_PASSWORD;
        byte[] key = new byte[16];
        for (int i = 0; i < password.length() && i < key.length; i++) {
            key[i] = (byte) password.charAt(i);
        }
        return key;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
}
