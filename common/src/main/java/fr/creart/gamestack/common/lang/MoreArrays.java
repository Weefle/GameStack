package fr.creart.gamestack.common.lang;

/**
 * Arrays utils
 *
 * @author Creart
 */
public class MoreArrays {

    /**
     * Returns the last object of the given array
     *
     * @param objects Array
     * @param <T>     Object types
     * @return the last object of the given array
     */
    public static <T> T lastObjectOf(T[] objects)
    {
        return objects[objects.length - 1];
    }

    /**
     * Concatenates the whole String array
     *
     * @param strings String array
     * @return the concatenation of all the strings in the array
     */
    public static String concat(String[] strings)
    {
        StringBuilder builder = new StringBuilder();
        for (String str : strings)
            builder.append(str);
        return builder.toString();
    }

}
