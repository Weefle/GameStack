package fr.creart.gamestack.common.lang;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.log.CommonLogger;
import java.lang.reflect.Field;

/**
 * Reflection utils
 *
 * @author Creart
 */
public final class ReflectionUtil {

    private ReflectionUtil()
    {

    }

    public static void changeFieldValue(String field, Class<?> clazz, Object instance, Object newValue)
    {
        try {
            getField(field, clazz).set(instance, newValue);
        } catch (Exception e) {
            CommonLogger.error("Could not change \"" + field + "\"'s value (class=" + clazz.getName() + ").", e);
        }
    }

    private static Field getField(String field, Class<?> clazz)
    {
        Preconditions.checkArgument(field != null && field.length() > 0, "field name can't be null or empty");
        Preconditions.checkNotNull(clazz, "class can't be null");

        try {
            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getName().equals(field))
                    return f;
            }
            return null;
        } catch (Exception e) {
            CommonLogger.error("Encountered an exception while accessing " + clazz.getSimpleName() + "'s fields.", e);
            return null;
        }
    }

    public static Object getFieldValue(String field, Class<?> clazz, Object instance)
    {
        try {
            return getField(field, clazz).get(instance);
        } catch (Exception e) {
            CommonLogger.error("Could not get \"" + field + "\"'s value (class=" + clazz.getName() + ").", e);
            return null;
        }
    }

}
