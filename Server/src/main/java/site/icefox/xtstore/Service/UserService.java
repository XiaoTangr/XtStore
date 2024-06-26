package site.icefox.xtstore.Service;

import com.mysql.cj.log.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import site.icefox.xtstore.Dao.GoodsDao;
import site.icefox.xtstore.Dao.UserDao;
import site.icefox.xtstore.Entities.Goods;
import site.icefox.xtstore.Entities.GoodsInCart;
import site.icefox.xtstore.Entities.User;
import site.icefox.xtstore.Utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    /**
     * 添加用户
     *
     * @param request  req
     * @param response resp
     */
    public static void insertUser(HttpServletRequest request, HttpServletResponse response) {
        String reqUserIDS = request.getParameter("UserID");
        String reqUserName = request.getParameter("UserName");
        String reqUserPwd = request.getParameter("Password");
        String reqUserPwd1 = request.getParameter("Password1");
        String reqUserPhone = request.getParameter("UserPhone");
        String reqUserAddr = request.getParameter("UserAddr");
        String reqUserCart = "";
        String reqUserType = request.getParameter("UserType");
        try {


            if (reqUserIDS == null || reqUserName == null || reqUserPwd == null || reqUserPwd1 == null || reqUserType == null || reqUserPhone == null || reqUserAddr == null) {
                RespSendUtil.sendErrorResponse(response, "缺少必要参数!");
                return;
            }
            long reqUserID = Long.parseLong(reqUserIDS);
            if (UserDao.isUserExist(reqUserID)) {
                RespSendUtil.sendErrorResponse(response, "UserID已存在");
                return;
            }
            if (!reqUserPwd.equals(reqUserPwd1)) {
                RespSendUtil.sendErrorResponse(response, "两次输入的密码不一致");
                return;
            }

            User OpUser = UserService.getUserInfoFromHeader(request);

            int UserType = 0;
            if (OpUser != null && OpUser.getUserType() == 2) {
                UserType = Integer.parseInt(reqUserType);
            }

            User newUser = new User();
            newUser.setUserID(reqUserID);
            newUser.setUserName(reqUserName);
            newUser.setPassword(MD5Util.getMd5ByString(reqUserPwd));
            newUser.setUserType(UserType);
            newUser.setUserPhone(reqUserPhone);
            newUser.setUserAddr(reqUserAddr);
            newUser.setUserCart(reqUserCart);
            System.out.println(newUser);
            if (UserDao.insertUser(newUser)) {
                RespSendUtil.sendSuccessResponse(response, "注册成功", newUser);
            } else {
                RespSendUtil.sendErrorResponse(response, "注册失败");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录
     *
     * @param request  req
     * @param response res
     */
    public static void login(HttpServletRequest request, HttpServletResponse response) {
        String reqUserID = request.getParameter("UserID");
        String reqUserPwd = request.getParameter("Password");
        try {


            if (reqUserID == null || reqUserPwd == null) {
                RespSendUtil.sendErrorResponse(response, "用户名或密码不能为空");
                return;
            }
            long UserID = Long.parseLong(reqUserID);
            User user = UserDao.queryOneUserByID(UserID);
            if (user == null) {
                RespSendUtil.sendErrorResponse(response, "用户不存在");
                return;
            }
            if (UserID != user.getUserID() || !MD5Util.compareMd5WithString(user.getPassword(), reqUserPwd)) {
                RespSendUtil.sendErrorResponse(response, "账号或密码错误");
                return;
            }
            Map<String, String> info = new HashMap<>();
            info.put("UserID", String.valueOf(user.getUserID()));
            info.put("UserName", user.getUserName());
            info.put("UserType", String.valueOf(user.getUserType()));

            String jwt = JwtUtil.encodeJwt(info);

            response.setHeader("Authorization", jwt);
            RespSendUtil.sendSuccessResponse(response, "登录成功", user);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从header中获取用户信息
     *
     * @param request req
     * @return User object or null
     */
    public static User getUserInfoFromHeader(HttpServletRequest request) {
        String Authorization = request.getHeader("Authorization");
        try {
            long UserID = Long.parseLong((String) JwtUtil.decodeJwt(Authorization).get("UserID"));
            return UserDao.queryOneUserByID(UserID);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 一般鉴权
     *
     * @param request  req
     * @param response resp
     */
    public static void isAuthed(HttpServletRequest request, HttpServletResponse response) {
        User user = UserService.getUserInfoFromHeader(request);
        try {
            if (user == null) {
                RespSendUtil.sendErrorResponse(response, "User not found!");
                return;
            }
            user = UserDao.queryOneUserByID(user.getUserID());
            response.setHeader("Authorization", request.getHeader("Authorization"));
            RespSendUtil.sendSuccessResponse(response, "Authorized!", user);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除用户,需要鉴权后调用
     *
     * @param request  req
     * @param response resp
     */
    public static void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        User reqUser = UserService.getUserInfoFromHeader(request);
        long UserIDtoDel;
        String respMsg = "未知的错误!";

        try {


            if (reqUser != null) {
                if (reqUser.getUserType() == 2) {
//                超级管理员需要传入删除的用户id
                    String reqUserID = request.getParameter("UserID");
                    if (reqUserID == null) {
                        RespSendUtil.sendErrorResponse(response, "缺少必要参数");
                        return;
                    }
                    UserIDtoDel = Long.parseLong(reqUserID);
                    respMsg = "管理员成功删除用户: " + UserIDtoDel;
                } else {
//                非管理员只能注销自己
                    UserIDtoDel = reqUser.getUserID();
                    respMsg = reqUser.getUserID() + "成功删除用户: " + UserIDtoDel;
                }
                if (!UserDao.isUserExist(UserIDtoDel)) {
                    RespSendUtil.sendErrorResponse(response, "不存在指定的用户: " + UserIDtoDel);
                    return;
                }
                if (UserDao.deleteUser(UserIDtoDel)) {
                    RespSendUtil.sendSuccessResponse(response, respMsg);
                } else {
                    RespSendUtil.sendErrorResponse(response);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 更新用户信息
     *
     * @param request  req
     * @param response res
     */
    public static void updateUser(HttpServletRequest request, HttpServletResponse response) {
        User reqUser = UserService.getUserInfoFromHeader(request);
        User resUser = null;
        String ParamResUserID = request.getParameter("UserType");
        long ResUserID;
        int ResUserType;
        try {
            if (reqUser == null) {
                RespSendUtil.sendErrorResponse(response, "User not found!");
                return;
            }
//          先设置UserType
            ResUserType = reqUser.getUserType();
//            设置resUserID
            resUser = UserDao.queryOneUserByID(reqUser.getUserID());
//            如果是管理员,则可修改其他用户的USerType,且必须指定修改的UserID
            if (reqUser.getUserType() > 1) {
                if (ParamResUserID == null) {
                    RespSendUtil.sendErrorResponse(response, "管理员需要指定UserID!");
                    return;
                }
                ResUserID = Integer.parseInt(ParamResUserID);
                if (request.getParameter("UserType") != null) {
                    ResUserType = Integer.parseInt(request.getParameter("UserType"));
                }
            } else {
                ResUserID = reqUser.getUserID();
            }

            resUser.setUserID(ResUserID);
            resUser.setUserType(ResUserType);
            //        如果任一有值则需要判断密码，否则不更改密码
            String oldPassword = request.getParameter("oldPassword");
            String Password = request.getParameter("Password");
            String Password1 = request.getParameter("Password1");

            if (Password != null || Password1 != null || oldPassword != null) {
                if (Password != null && Password1 != null && oldPassword != null) {
                    if (!MD5Util.compareMd5WithString(reqUser.getPassword(), oldPassword)) {
                        RespSendUtil.sendErrorResponse(response, "原密码验证失败");
                        return;
                    }
                    if (!Password.equals(Password1)) {
                        RespSendUtil.sendErrorResponse(response, "两次密码不一致");
                        return;
                    }
                    reqUser.setPassword(MD5Util.getMd5ByString(request.getParameter("Password")));

                } else {
                    RespSendUtil.sendErrorResponse(response, "缺少必要参数");
                    return;
                }
            }


            if (request.getParameter("UserName") != null)
                reqUser.setUserName(request.getParameter("UserName"));

            if (request.getParameter("UserPhone") != null)
                reqUser.setUserPhone(request.getParameter("UserPhone"));
            if (request.getParameter("UserAddr") != null)
                reqUser.setUserAddr(request.getParameter("UserAddr"));
            if (request.getParameter("UserCart") != null)
                reqUser.setUserCart(request.getParameter("UserCart"));

            if (UserDao.updateUser(resUser)) {
                RespSendUtil.sendSuccessResponse(response, resUser);
            } else {
                RespSendUtil.sendErrorResponse(response);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取用户信息,一个或者全部
     *
     * @param request  req
     * @param response res
     */
    public static void getUser(HttpServletRequest request, HttpServletResponse response) {

        User reqUser = UserService.getUserInfoFromHeader(request);
        String paramUserID = request.getParameter("UserID");
        long UserID;
        try {
            if (reqUser == null) {
                RespSendUtil.sendErrorResponse(response);
                return;
            }
            if (reqUser.getUserType() != 2) {
                User resUser = UserDao.queryOneUserByID(reqUser.getUserID());
                RespSendUtil.sendSuccessResponse(response, resUser);
                return;
            }
            if (paramUserID != null) {
                UserID = Long.parseLong(paramUserID);
                User resUser = UserDao.queryOneUserByID(UserID);
                RespSendUtil.sendSuccessResponse(response, resUser);
            } else {
                List<User> resUserList = UserDao.queryAllUser();
                RespSendUtil.sendSuccessResponse(response, resUserList);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean updateUserCartByStr(long UserID, String CartStr) {
        List<GoodsInCart> reqList = JsonUtil.toObjects(CartStr, GoodsInCart.class);
        List<GoodsInCart> resultList = new ArrayList<>();
        try {

            for (GoodsInCart goodsInCart : reqList) {
                if (!GoodsService.isGoodsExist(goodsInCart.getGoodsID())) {
                    continue;
                }
                Goods goods = GoodsDao.queryOneGoodsById(goodsInCart.getGoodsID());
                goodsInCart.setGoodsPerPrice(goods.getGoodsPerPrice());
                resultList.add(goodsInCart);
            }
            String resultStr = JsonUtil.toJson(resultList);
            return UserDao.updateUserCart(UserID, resultStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

