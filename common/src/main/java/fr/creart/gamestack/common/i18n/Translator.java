package fr.creart.gamestack.common.i18n;

import fr.creart.gamestack.common.io.FileUtil;
import fr.creart.gamestack.common.log.CommonLogger;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Util class which allows to obtain translations from the "messages.properties" file and replace arguments ("{\d}")
 * The translations are user-related.
 *
 * @author Creart
 */
public class Translator {

    private static final String MESSAGES_FILE = "messages.properties";

    private static boolean initialized;
    private static ResourceBundle resourceBundle;

    private Translator()
    {

    }

    /**
     * Initializes the translator.
     */
    public static void initialize()
    {
        if (initialized)
            return;

        try {
            initialize(false);
        } catch (Exception e) {
            try {
                initialize(true);
            } catch (Exception e1) {
                CommonLogger.error(String.format("Could not load %s file!", MESSAGES_FILE), e);
            }
        }

        initialized = true;
    }

    /**
     * Returns the translated text (all arguments replaced by the objects).
     *
     * @param path    String's path
     * @param objects Objects to replace arguments
     * @return the translated text (all arguments replaced by the objects).
     */
    public static String translate(String path, Object... objects)
    {
        String ret;

        try {
            ret = MessageFormat.format(resourceBundle.getString(path), objects);
        } catch (Exception e) {
            CommonLogger.error(String.format("Could not get String %s in %s.", path, MESSAGES_FILE), e);
            ret = "/!\\ translation missing '" + path + "' /!\\";
        }

        return ret;
    }

    private static void initialize(boolean retry) throws IOException
    {
        if (retry && !FileUtil.saveResource(MESSAGES_FILE, MESSAGES_FILE, true).isSuccess())
            throw new IOException("Could not create messages.properties file.");

        FileInputStream in = new FileInputStream(MESSAGES_FILE);
        resourceBundle = new PropertyResourceBundle(in);
    }

}
