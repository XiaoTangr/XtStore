package site.icefox.xtstore.Utils;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ParamCheckUtil {

    /**
     * 检查请求参数是否包含指定实体类的所有属性
     *
     * @param request       HttpServletRequest 请求对象
     * @param entityClass   实体类的 Class 对象
     * @return 如果请求包含所有属性，返回 true，否则返回 false
     */
    public static boolean hasAllParams(HttpServletRequest request, Class<?> entityClass) {
        // 获取实体类的所有字段
        Field[] fields = entityClass.getDeclaredFields();
        Set<String> fieldNames = new HashSet<>();

        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        // 检查请求参数是否包含所有字段
        for (String fieldName : fieldNames) {
            if (request.getParameter(fieldName) == null) {
                return false; // 如果有任何一个字段在请求参数中不存在，返回 false
            }
        }

        return true; // 所有字段都在请求参数中存在，返回 true
    }
}
