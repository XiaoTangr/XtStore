package site.icefox.xtstore.Utils;

import java.util.Base64;

public class Base64Util {
    /**
     * 对字符串进行Base64编码
     *
     * @param input 要编码的字符串
     * @return 编码后的字符串
     */
    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    /**
     * 对Base64编码的字符串进行解码
     *
     * @param input 要解码的字符串
     * @return 解码后的字符串
     */
    public static String decode(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }
}
