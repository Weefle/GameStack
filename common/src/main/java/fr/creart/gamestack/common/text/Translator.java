/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.text;

import fr.creart.gamestack.common.io.FileUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * Util class which allows to obtain translations from the "messages.properties" file and replace arguments ("{\d}")
 * The translations are user-related.
 *
 * @author Creart
 */
public class Translator {

    private static final Logger LOGGER = Logger.getLogger(Translator.class);

    private String file;
    private boolean initialized;
    private ResourceBundle resourceBundle;

    public Translator(String file)
    {
        this.file = file;
    }

    /**
     * Initializes the translator.
     */
    public void initialize()
    {
        if (initialized)
            return;

        try {
            initialize(false);
        } catch (Exception e) {
            LOGGER.error(String.format("Could not load %s file. Trying again...", file), e);
            try {
                initialize(true);
            } catch (Exception e1) {
                LOGGER.error(String.format("Could not load %s file!", file), e1);
                return;
            }
        }

        LOGGER.info("Successfully loaded messages.properties file.");
        initialized = true;
    }

    /**
     * Returns the translated text (all arguments replaced by the objects).
     *
     * @param path    String's path
     * @param objects Objects to replace arguments
     * @return the translated text (all arguments replaced by the objects).
     */
    public String translate(String path, Object... objects)
    {
        String ret;

        try {
            ret = MessageFormat.format(resourceBundle.getString(path), objects);
        } catch (Exception e) {
            LOGGER.error(String.format("Could not get String %s in %s.", path, file), e);
            ret = "/!\\ translation missing '" + path + "' /!\\";
        }

        return ret;
    }

    private void initialize(boolean retry) throws IOException
    {
        if (retry && !FileUtil.saveResource(file, file, true).isSuccess())
            throw new IOException("Could not create messages.properties file.");

        FileInputStream in = new FileInputStream(file);
        resourceBundle = new PropertyResourceBundle(in);
    }

}
