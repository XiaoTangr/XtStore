package site.icefox.xtstore.Utils.Interface.Impl;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import site.icefox.xtstore.Utils.Interface.JsonExclude;

public class JsonExcludeImpl implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(JsonExclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
