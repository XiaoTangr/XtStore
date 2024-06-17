package site.icefox.xtstore.Utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import site.icefox.xtstore.Utils.Interface.JsonExclude;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setExclusionStrategies(new JsonExclusionStrategy())
            .create();

    /**
     * 将对象转换为JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz 对象类
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * 将对象列表转换为JSON字符串
     *
     * @param objList 对象列表
     * @param <T>     泛型
     * @return JSON字符串
     */
    public static <T> String toJson(List<T> objList) {
        return gson.toJson(objList);
    }

    /**
     * 将JSON字符串转换为指定类型的对象列表
     *
     * @param json  JSON字符串
     * @param clazz 对象类
     * @param <T>   泛型
     * @return 对象列表
     */
    public static <T> List<T> toObjects(String json, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, typeOfT);
    }

    private static class JsonExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(JsonExclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
