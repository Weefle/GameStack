package fr.creart.gamestack.common.log;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

/**
 * A common logger for all GameStack softwares.
 *
 * @author Creart
 */
public class CommonLogger {

    private static Logger logger;

    /**
     * Creates a new logger if it has not been done yet.
     *
     * @param softName  The name of the software which is using the logger.
     */
    public static void createLogger(String softName)
    {
        if (!hasLogger()) {
            Preconditions.checkNotNull(Strings.emptyToNull(softName), "soft name can't be null or empty.");
            logger = Logger.getLogger(softName);
        }
    }

    /**
     * Returns {@code true} if the logger has been created.
     *
     * @return {@code true} if the logger has been created.
     */
    public static boolean hasLogger()
    {
        return logger != null;
    }

    public static void info(String message)
    {
        logger.info(message);
    }

    public static void error(String message)
    {
        logger.error(message);
    }

    public static void error(String message, Throwable attached)
    {
        logger.error(message, attached);
    }

    public static void fatal(String message)
    {
        logger.fatal(message);
    }

    public static void fatal(String message, Throwable attached)
    {
        logger.fatal(message, attached);
    }

}
