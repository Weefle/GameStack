package fr.creart.gamestack.common.misc;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

/**
 * Json utils
 *
 * @author Creart
 */
public final class JsonUtil {

    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().create();

    private JsonUtil()
    {

    }

    /**
     * Returns a textual json representation of the given object
     *
     * @param obj Object to serialize
     * @return a textual json representation of the given object
     */
    public static String toJson(Object obj)
    {
        Preconditions.checkNotNull(obj, "object can't be null");

        return GSON.toJson(obj);
    }

    /**
     * Returns the object deserialized from the string
     *
     * @param clazz source class
     * @param json  source String
     * @return the object deserialized from the string
     */
    public static <T> T fromJson(Class<T> clazz, String json)
    {
        return GSON.fromJson(json, (Type) clazz);
    }

}
