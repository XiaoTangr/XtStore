package site.icefox.xtstore.Entities;

import lombok.Data;
import site.icefox.xtstore.Utils.Interface.JsonExclude;

@Data
public class User {
    private long UserID;
    private String UserName;
    @JsonExclude
    private String Password;
    private int UserType;
    private String UserCart;
    private String UserPhone;
    private String UserAddr;
}
