package com.prajnasys.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {
    
    private final static String[] strArr = {"1", "2", "3", "4", "5", "6", "7", 
        "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", 
        "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", 
        "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", 
        "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final static String[] numberArr = {"1", "2", "3", "4", "5", "6", "7", 
        "8", "9", "0"};

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param String s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     *
     * @param String s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1    
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符    
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符    
            if (temp.matches(chinese)) {
                // 中文字符长度为1    
                valueLength += 1;
            } else {
                // 其他字符长度为0.5    
                valueLength += 0.5;
            }
        }
        //进位取整    
        return Math.ceil(valueLength);
    }
    
    /**
     * 创建随机字符串
     * @param length 字符串码长度
     * @return 
     */
    public static String getRandomStr(int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++){
            sb.append(strArr[random.nextInt(strArr.length)]);
        }
        
        return sb.toString();
    }
    
    /**
     * 创建随机字符串
     * @param length 字符串码长度
     * @return 
     */
    public static String getRandomNumber(int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++){
            sb.append(numberArr[random.nextInt(numberArr.length)]);
        }
        return sb.toString();
    }
    
    /**
     * 过滤utf8字符串中长度为4的字符
     * @param text
     * @return 过滤后的字符串
     * @throws UnsupportedEncodingException 
     */
    public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("utf-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }
            b += 256;
            if ((b ^ 0xC0) >> 4 == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            }
            else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            }
            else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            }
        }
        buffer.flip();
        return new String(buffer.array(), "utf-8");
    }
    
    /**
     * 将URL中的参数解码成utf-8格式的字符串
     * @param str URL参数
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String decodeURL(String str) throws UnsupportedEncodingException{
        return decodeURL(str, "utf-8");
    }
    
    /**
     * 将URL中的参数解码成"charset"格式的字符串
     * @param str    URL参数
     * @param charset 编码类型，如"utf-8"、"gbk"等
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String decodeURL(String str, String charset) throws UnsupportedEncodingException{
        if(str == null || "".equals(str)){
            return str;
        }
        return URLDecoder.decode(str.replaceAll("%", "%25"), charset);
    }
    
    /**
     * 判断字符是否为空
     * @param str
     * @return 如果传入null或""，则返回fase，否则返回true
     */
    public static boolean isNotEmpty(String str){
        return str != null && !"".equals(str);
    }
    
    /**
     * 按照指定格式获取当前时间字符串
     * @param format 如"yyyy-MM-dd HH:mm:ss"
     * @return 
     */
    public static String getCurrDate(String format){
        return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
    }
    
    /**
     * 格式化指定时间
     * @param date 
     * @param format 如"yyyy-MM-dd HH:mm:ss"
     * @return 
     */
    public static String formatDate(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }
    
    /**
     * 将数字按格式转换为字符串
     * @param d 需要转换的数字
     * @param format 格式，例如("0.00")
     * @return 
     */
    public static String formatNumber(double d, String format){
        DecimalFormat df = new DecimalFormat("######" + format);   
        return df.format(d);
    }
    
    /**
     * 将byte[]转换为16进制字符串
     * @param src
     * @return 
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将16进制字符串转换为byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    
    /**
     * 将数字转换成指定长度的字符串，长度不足时在前面补0
     * @param x 需要转换的数字
     * @param len 期望长度
     * @return 
     */
    public static String convertStr(int x, int len){
        StringBuilder sb = new StringBuilder();
        String str = x + "";
        while((sb.length() + str.length()) < len){
            sb.append("0");
        }
        return sb.toString() + x;
    }
    
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * 判断字符串item是否存在于字符串数组array中
     * @param array
     * @param item
     * @return 
     */
    public static boolean isExist(String[] array, String item){
        if(array == null){
            return false;
        }
        for(String str : array){
            if(str != null && str.equals(item)){
                return true;
            }
        }
        return false;
    }
}