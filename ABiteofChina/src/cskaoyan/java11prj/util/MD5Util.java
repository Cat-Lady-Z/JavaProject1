package cskaoyan.java11prj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/14
 * Time: 下午 9:13
 * Detail requirement:
 * Method:
 */
public class MD5Util {
    static String ALGORITHM = "MD5";
    static String SHA_ALGORITHM = "sha_512";

    //1、没有加严的方法
    public static String encrypt(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            //获得字节数组
            byte[] bytes = password.getBytes();
            //根据字节数组执行加密
            byte[] resultBytes = digest.digest(bytes);

            //获得结果的字节，将其转换成字符串
            String encryptResult = byteArrayToString(resultBytes);
            
            return encryptResult;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToString(byte[] resultBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte byte1:resultBytes) {
            int x = byte1&0xff;
            String s = Integer.toHexString(x);
            if (s.length() == 1){
                stringBuffer.append("0");
            }
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    //2、加严的方法，一般加pid
    public static String encrypt(String password,String sault){
        try {
            //1.拿到MD5的摘要
            MessageDigest instance = MessageDigest.getInstance(ALGORITHM);
            //2、转换成一个字节
            password = password + sault;
            byte[] passwordBytes = password.getBytes();

            //md5值加密
            byte[] digest = instance.digest(passwordBytes);

            String encryptResult = byteArrayToString(digest);
            return encryptResult;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}