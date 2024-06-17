import org.junit.jupiter.api.Test;
import site.icefox.xtstore.Utils.MD5Util;

public class MD5Test {
    @Test
    public void Md5Test() {
        String string = "admin";
        System.out.println(MD5Util.getMd5ByString(string));
    }
}
