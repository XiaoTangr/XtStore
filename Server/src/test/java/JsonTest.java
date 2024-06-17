import org.junit.jupiter.api.Test;
import site.icefox.xtstore.Entities.Goods;
import site.icefox.xtstore.Entities.GoodsInCart;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {

    @Test
    public void JsontoObjTest() {
        User user = new User();
        Goods newGoods = new Goods();
        newGoods.setGoodsID(1);
        newGoods.setGoodsName(String.valueOf(1));
        newGoods.setGoodsDesc(String.valueOf(1));
        newGoods.setGoodsImg(String.valueOf(1));
        newGoods.setGoodsPerPrice(1.0);
        newGoods.setGoodsPerUnit(String.valueOf(1));
        newGoods.setGoodsInven(1);
        newGoods.setGoodsCate("1");

        System.out.println(JsonUtil.toJson(newGoods));
    }

    @Test
    public void JsontoObjTests() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserID(1232);
            user.setUserName("icefox" + i);
            user.setPassword("123456");
            user.setUserType(2);

            users.add(user);
        }


        System.out.println(JsonUtil.toJson(users));
    }

    @Test
    public void getCarts() {
        List<GoodsInCart> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsInCart goodsInCart = new GoodsInCart();
            goodsInCart.setGoodsID(i);
            goodsInCart.setGoodsName("测试" + i);
            goodsInCart.setCountNum(i);
            goodsInCart.setGoodsPerPrice(i);
            goodsInCart.setGoodsImg("#");
            list.add(goodsInCart);
        }
        System.out.println(JsonUtil.toJson(list));

    }
}
