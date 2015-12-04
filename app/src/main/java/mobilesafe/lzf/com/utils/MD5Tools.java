package mobilesafe.lzf.com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tools {

    public static String getMd5Str(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] bytes = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                int number = b & 0xff;//加盐
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(hex);
            }
            //md5加密后的值
            System.out.println(buffer);
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
