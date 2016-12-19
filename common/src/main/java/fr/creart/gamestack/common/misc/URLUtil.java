/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.misc;

import java.net.URL;
import org.apache.commons.lang3.Validate;

/**
 * @author Creart
 */
public final class URLUtil {

    private URLUtil()
    {
        // no instance
    }

    /**
     * Returns <tt>true</tt> if the given URL is valid
     *
     * @param url the String to test
     * @return <tt>true</tt> if the given URL is valid
     */
    public static boolean isValidURL(String url)
    {
        Validate.notEmpty(url, "url cannot be null");

        try {
            URL u = new URL(url);
            u.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
