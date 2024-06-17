import org.junit.jupiter.api.Test;
import site.icefox.xtstore.Utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testJwt() {
        Map<String, String> Claims = new HashMap<>();
        Claims.put("key1", "value1");
        Claims.put("key2", "value2");
        Claims.put("key3", "value3");

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyVHlwZSI6IjIiLCJ1c2VyTmFtZSI6ImFkbWluIiwidXNlcklEIjoiMTAwMDAxIiwiaWF0IjoxNzE4Mjk1MjY4LCJleHAiOjE3MTgzMzg0Njh9.oeKa01a2YThKS7lvTLcHZD-KH0YgiVJ2vAUb7RXQ1xM";
        System.out.println(jwt);


        Map<String, Object> decodedClaims = JwtUtil.decodeJwt(jwt);
        System.out.println(decodedClaims);
    }
}
