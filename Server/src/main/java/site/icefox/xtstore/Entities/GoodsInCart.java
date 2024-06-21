package site.icefox.xtstore.Entities;

import lombok.Data;

@Data
public class GoodsInCart {
    private int CountNum;
    private double GoodsPerPrice;
    private int GoodsID;
    private String GoodsName;
}
