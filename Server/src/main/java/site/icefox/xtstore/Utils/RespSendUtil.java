package site.icefox.xtstore.Utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RespSendUtil {


    /**
     * 发送无参数的成功响应
     *
     * @param resp resp
     * @throws IOException e
     */
    public static void sendSuccessResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.success());
        out.flush();
    }

    /**
     * 发送带有消息的成功响应
     *
     * @param resp resp
     * @param msg  msg
     * @throws IOException e
     */
    public static void sendSuccessResponse(HttpServletResponse resp, String msg) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.success(msg));
        out.flush();
    }


    /**
     * 发送带有Data的成功响应
     *
     * @param resp resp
     * @param data data
     * @param <T>  T
     * @throws IOException e
     */
    public static <T> void sendSuccessResponse(HttpServletResponse resp, T data) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.success(data));
        out.flush();
    }

    public static <T> void sendSuccessResponse(HttpServletResponse resp, String msg, T data) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.success(msg, data));
        out.flush();
    }


    /**
     * 发送未知错误的失败响应
     *
     * @param resp resp
     * @throws IOException e
     */
    public static void sendErrorResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.error());
        out.flush();
    }

    /**
     * 发送带有失败信息的失败响应
     *
     * @param resp resp
     * @param msg  msg
     * @throws IOException e
     */
    public static void sendErrorResponse(HttpServletResponse resp, String msg) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(RespJsonUtil.error(msg));
        out.flush();
    }
}
