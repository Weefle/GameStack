package fr.creart.gamestack.common.lang;

import java.util.function.Predicate;

/**
 * Some stream utils
 *
 * @author Creart
 */
public final class Streams {

    private Streams()
    {

    }

    /**
     * Removes all null objects
     *
     * @return a predicate which removes all null objects
     */
    public static <T> Predicate<T> nonNullFilter()
    {
        return t -> t != null;
    }

}
