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

package fr.creart.gamestack.common.log;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import fr.creart.gamestack.common.io.FileUtil;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

/**
 * A common logger for all GameStack softwares.
 *
 * @author Creart
 */
public final class CommonLogger {

    private static final Pattern LOG_FILE_PATTERN = Pattern.compile("^log(.*)*\\.txt$", Pattern.CASE_INSENSITIVE);

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
            Preconditions.checkNotNull(Strings.emptyToNull(softName), "soft name can't be null or empty.");
            logger = Logger.getLogger(softName);
            logger.addAppender(new ConsoleAppender(new CustomLayout(false)));

            try {
                File output = new File("logs/" + softName.
                        toLowerCase() + "/log-" + DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss").format(LocalDateTime.now()) + ".txt");

                /*
                Before, the log files were compressed when they were too much (5), although the function listFiles was so slow that
                I had to change it; now, the older logs are compressed when the session starts.
                 */

                if (!output.getParentFile().exists())
                    output.getParentFile().mkdirs();

                File[] files = output.getParentFile().listFiles(pathname -> LOG_FILE_PATTERN.matcher(pathname.getName()).matches());
                for (File uncompressed : files) {
                    File archive = new File(output.getParentFile().getPath() + File.separator + FileUtil.getFileCleanName(uncompressed) + ".zip");

                    if (!archive.exists())
                        archive.createNewFile();

                    if (FileUtil.addToZip(archive, files))
                        uncompressed.delete();
                }

                if (!output.exists())
                    output.createNewFile();

                FileAppender appender = new FileAppender(new CustomLayout(true), output.getAbsolutePath(), false);
                logger.addAppender(appender);
            } catch (Exception e) {
                CommonLogger.error("Could not add file appender for logging!", e);
            }
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

}
