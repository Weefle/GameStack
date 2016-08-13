/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Floating point utils
 *
 * @author Creart
 */
public class Decimals {

    private Decimals()
    {

    }

    /**
     * Returns the first decimals of the number
     *
     * @param number   Number
     * @param decimals Number of decimals
     * @return the first decimals of the number
     */
    public static String firstDecimals(double number, int decimals)
    {
        if ((int) Math.round(number) == 0 || decimals == 0)
            return "";
        int dec = decimals;
        if (dec < 0)
            dec = -decimals;
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#." + MoreStrings.repeat('#', dec));
        format.setDecimalFormatSymbols(symbols);
        return format.format(number);
    }

    /**
     * Returns <tt>true</tt> if the two floats are equal
     *
     * @param first  First value
     * @param second Second value
     * @return <tt>true</tt> if the two values are equal
     */
    public static boolean equal(float first, float second)
    {
        return Float.floatToIntBits(first) == Float.floatToIntBits(second);
    }

    /**
     * Returns <tt>true</tt> if the two doubles are equal
     *
     * @param first  First value
     * @param second Second value
     * @return <tt>true</tt> if the two doubles are equal
     */
    public static boolean equal(double first, double second)
    {
        return Double.doubleToLongBits(first) == Double.doubleToLongBits(second);
    }

}
