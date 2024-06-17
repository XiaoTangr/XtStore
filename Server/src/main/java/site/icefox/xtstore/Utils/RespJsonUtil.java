package site.icefox.xtstore.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import site.icefox.xtstore.Utils.Interface.Impl.JsonExcludeImpl;

public class RespJsonUtil<T> {

    private int code; // 200 表示成功，500 表示失败
    private String msg;
    private T data;

    public RespJsonUtil(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new JsonExcludeImpl())
                .create();
    }

    /**
     * 无参数的成功响应
     *
     * @param <T> T
     * @return Json
     */
    public static <T> String success() {
        RespJsonUtil<T> result = new RespJsonUtil<>(200, "success", null);
        return createGson().toJson(result);
    }

    /**
     * 带消息但是没有数据的成功响应
     *
     * @param msg 消息
     * @param <T> T
     * @return Json
     */
    public static <T> String success(String msg) {
        RespJsonUtil<T> result = new RespJsonUtil<>(200, msg, null);
        return createGson().toJson(result);
    }

    /**
     * 有参数的成功响应
     *
     * @param data 数据
     * @param <T>  T
     * @return Json
     */
    public static <T> String success(T data) {
        RespJsonUtil<T> result = new RespJsonUtil<>(200, "success", data);
        return createGson().toJson(result);
    }

    /**
     * 带有消息和数据的成功响应
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  T
     * @return Json
     */
    public static <T> String success(String msg, T data) {
        RespJsonUtil<T> result = new RespJsonUtil<>(200, msg, data);
        return createGson().toJson(result);
    }

    /**
     * 失败响应
     *
     * @param <T> T
     * @return Json
     */
    public static <T> String error() {
        RespJsonUtil<T> result = new RespJsonUtil<>(500, "未知错误", null);
        return createGson().toJson(result);
    }

    /**
     * 失败响应
     *
     * @param msg 消息
     * @param <T> T
     * @return Json
     */
    public static <T> String error(String msg) {
        RespJsonUtil<T> result = new RespJsonUtil<>(500, msg, null);
        return createGson().toJson(result);
    }

}
