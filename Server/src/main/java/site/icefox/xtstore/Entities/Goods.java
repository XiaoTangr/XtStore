package site.icefox.xtstore.Entities;

import lombok.Data;

@Data
public class Goods {

    private int GoodsID;

    private String GoodsName;
    private String GoodsDesc;
    private String GoodsImg;

    private Double GoodsPerPrice;
    private String GoodsPerUnit;

    private int GoodsInven;
    private String GoodsCate;
}
