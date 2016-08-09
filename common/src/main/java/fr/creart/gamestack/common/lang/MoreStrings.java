/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

/**
 * String utils
 *
 * @author Creart
 */
public final class MoreStrings {

    private MoreStrings()
    {

    }

    /**
     * Returns a String which repeats the given character the demanded amount of times.
     *
     * @param c     Character to repeat
     * @param times Times to repeat
     * @return a String which repeats the given character the demanded amount of times.
     */
    public static String repeat(char c, int times)
    {
        if (times == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        int fixed = times;
        if (fixed < 0)
            fixed = -fixed;
        for (int i = 0; i < fixed; i++)
            builder.append(c);
        return builder.toString();
    }

}
