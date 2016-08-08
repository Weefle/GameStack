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

    private static final byte MAX_UNCOMPRESSED_LOG_FILES = 4;
    private static final Pattern LOG_FILE_PATTERN = Pattern.compile("^log(.*)*\\.txt$",
            Pattern.CASE_INSENSITIVE);

    private static Logger logger;

    private CommonLogger()
    {

    }

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
            logger.addAppender(new ConsoleAppender(new CustomLayout(false)));

            try {
                File output = new File("logs/" + softName.
                        toLowerCase() + "/log-" + DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss").format(LocalDateTime.now()) + ".txt");

                File[] files = output.getParentFile().listFiles(file -> file != null && file.isFile() && LOG_FILE_PATTERN.matcher(file.getName()).matches());

                if (files != null && files.length > MAX_UNCOMPRESSED_LOG_FILES) {
                    File archive = new File("logs/" + softName.toLowerCase() + "/logs-" + DateTimeFormatter
                            .ofPattern("dd-MM-yyyy_HH-mm-ss").format(LocalDateTime.now()) + ".zip");
                    if (!archive.exists())
                        archive.createNewFile();
                    if (FileUtil.addToZip(archive, files))
                        for (File file : files)
                            file.delete();
                }

                if (!output.exists()) {
                    output.getParentFile().mkdirs();
                    output.createNewFile();
                }

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

    public static void info(String message)
    {
        logger.info(message);
    }

    public static void warn(String message)
    {
        logger.warn(message);
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
