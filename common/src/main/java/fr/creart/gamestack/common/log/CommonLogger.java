/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A common logger for all GameStack software.
 *
 * @author Creart
 */
public final class CommonLogger {

    private static Logger logger;

    private CommonLogger()
    {

    }

    /**
     * Creates a new logger if it has not been done yet.
     *
     * @param softName The name of the software which is using the logger.
     */
    public static void createLogger(String softName)
    {
        if (!hasLogger()) {
            System.setProperty("gamestack.log_folder", "logs/" + softName.toLowerCase() + "/");
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

    /**
     * Logs the given message as an information
     *
     * @param message The message
     */
    public static void info(String message)
    {
        logger.info(message);
    }

    /**
     * Logs the given message and the attached throwable as an information
     *
     * @param message  the message (https://goo.gl/g1aNsY)
     * @param attached attached throwable
     */
    public static void info(String message, Throwable attached)
    {
        logger.info(message, attached);
    }

    /**
     * Logs the given message as a warning
     *
     * @param message The message
     */
    public static void warn(String message)
    {
        logger.warn(message);
    }

    /**
     * Logs the given message as an error
     *
     * @param message The message
     */
    public static void error(String message)
    {
        logger.error(message);
    }

    /**
     * Logs the given message and the attached throwable as an error
     *
     * @param message  The message
     * @param attached The attached throwable
     */
    public static void error(String message, Throwable attached)
    {
        logger.error(message, attached);
    }

    /**
     * Logs the given message as a fatal error
     *
     * @param message The message
     */
    public static void fatal(String message)
    {
        logger.fatal(message);
    }

    /**
     * Logs the given message and the attached throwable as a fatal error
     *
     * @param message  The message
     * @param attached The attached throwable
     */
    public static void fatal(String message, Throwable attached)
    {
        logger.fatal(message, attached);
    }

    /**
     * Closes all created loggers
     */
    public static void close()
    {
        LogManager.shutdown();
    }

}
