package site.icefox.xtstore.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JwtUtil {
    private static final String SECRET_KEY_STRING = "XiaoTangXiaoTangXiaoTangXiaoTangXiaoTangXiaoTang";
    private static final String ENCODED_SECRET_KEY_STRING = Base64Util.encode(SECRET_KEY_STRING);
    private static final SecretKey SECRET_KEY = new SecretKeySpec(Base64Util.decode(ENCODED_SECRET_KEY_STRING).getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    private static final long EXPIRATION_TIME = 3600000 * 12; // 12 hours in milliseconds

    private static final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * 根据给定的键值对生成JWT
     *
     * @param claims 键值对Map
     * @return 生成的JWT字符串
     */
    public static String encodeJwt(Map<String, String> claims) {
        Map<String, Object> claimsMap = new HashMap<>(claims);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 根据键解析JWT获取值
     *
     * @param jwt JWT字符串
     * @param key 要解析的键
     * @return 解析出的值
     */
    public static String parseJwt(String jwt, String key) {
        if (isTokenBlacklisted(jwt)) {
            return null;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get(key, String.class);
        } catch (Exception e) {
            // 解析失败返回 null
            return null;
        }
    }

    /**
     * 验证JWT是否合法
     *
     * @param jwt  JWT字符串
     * @param keys 要检查的键数组
     * @return JWT是否合法的布尔值
     */
    public static boolean validateJwt(String jwt, String[] keys) {
        if (isTokenBlacklisted(jwt)) {
            return false;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            // 检查是否所有指定的键都存在
            for (String key : keys) {
                if (claims.get(key) == null) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据JWT获取所有键值对
     *
     * @param jwt JWT字符串
     * @return 键值对Map
     */
    public static Map<String, Object> decodeJwt(String jwt) {
        if (isTokenBlacklisted(jwt)) {
            return null;
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将JWT加入黑名单使其失效
     *
     * @param jwt 要使失效的JWT
     */
    public static void invalidateJwt(String jwt) {
        blacklistedTokens.add(jwt);
    }

    /**
     * 检查JWT是否在黑名单中
     *
     * @param jwt 要检查的JWT
     * @return 是否在黑名单中的布尔值
     */
    private static boolean isTokenBlacklisted(String jwt) {
        return blacklistedTokens.contains(jwt);
    }
}
