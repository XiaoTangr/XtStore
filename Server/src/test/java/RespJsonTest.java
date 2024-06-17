import org.junit.jupiter.api.Test;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Utils.RespJsonUtil;

public class RespJsonTest {
    @Test
    public void respJsonTest() {
        User user = new User();
        user.setUserID(1232);
        user.setUserName("icefox");
        user.setPassword("123456");
        user.setUserType(2);


        System.out.println(RespJsonUtil.success(user));
    }
}
