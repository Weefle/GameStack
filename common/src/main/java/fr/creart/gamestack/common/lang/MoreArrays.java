/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

import com.google.common.base.Preconditions;
import java.util.function.Predicate;

/**
 * Arrays utils
 *
 * @author Creart
 */
public final class MoreArrays {

    private MoreArrays()
    {

    }

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

    /**
     * Tests the given predicate on each given object.
     * Returns <code>true</code> if of all the objects passed predicate's test
     *
     * @param objects   Object array
     * @param predicate The predicate
     * @return <code>true</code> if of all the objects passed predicate's test
     */
    public static <T> boolean isTrueForAll(T[] objects, Predicate<T> predicate)
    {
        Preconditions.checkNotNull(predicate, "predicate can't be null");
        Preconditions.checkArgument(objects.length > 0, "objects can't be null");

        for (T obj : objects)
            if (!predicate.test(obj))
                return false;
        return true;
    }

}
