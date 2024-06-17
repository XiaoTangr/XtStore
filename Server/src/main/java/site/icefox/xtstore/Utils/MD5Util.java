package site.icefox.xtstore.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class MD5Util {
    /**
     * 根据输入字符串生成MD5哈希值
     *
     * @param input 输入字符串
     * @return MD5哈希值的十六进制字符串表示
     */
    public static String getMd5ByString(String input) {
        try {
            // 创建MD5消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入字符串的字节添加到摘要中
            md.update(input.getBytes());
            // 获取哈希的字节数组
            byte[] bytes = md.digest();
            // 将字节数组转换为十六进制格式
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            // 获取完整的十六进制格式哈希字符串
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 比较输入字符串的MD5哈希值与给定的MD5哈希字符串是否相匹配
     *
     * @param md5Hash 给定的MD5哈希字符串
     * @param string   输入字符串
     * @return 如果MD5哈希值匹配则返回true，否则返回false
     */
    public static boolean compareMd5WithString(String md5Hash, String string) {
        // 获取输入字符串的MD5哈希值
        String inputMd5 = getMd5ByString(string);
        // 比较计算的MD5哈希值与提供的MD5哈希字符串
        return Objects.equals(inputMd5, md5Hash);
    }
}
