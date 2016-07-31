package fr.creart.gamestack.common.log;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A common logger for all GameStack softwares.
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
     * @param softName  The name of the software which is using the logger.
     */
    public static void createLogger(String softName)
    {
        if (!hasLogger()) {
            Preconditions.checkNotNull(Strings.emptyToNull(softName), "soft name can't be null or empty.");
            logger = Logger.getLogger(softName);
            logger.addAppender(new ConsoleAppender(new CustomLayout(false)));
            File log = new File("logs/" + softName.toLowerCase() + "/log" + LocalDateTime.now().
                    format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")) + ".txt");
            if (log.delete())
                info("Log file already existed. Removed it.");

            try {
                if (log.getParentFile().mkdirs() && log.createNewFile())
                    info("Successfully created new log file.");
            } catch (Exception e) {
                error("Could not create new log file.", e);
            }

            try {
                logger.addAppender(new FileAppender(new CustomLayout(true), log.getAbsolutePath()));
            } catch (Exception e) {
                error("Could not add file appender to log4j.", e);
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

    private static class CustomLayout extends Layout {

        private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("'['HH':'mm']' ");

        private boolean file;

        private CustomLayout(boolean file)
        {
            this.file = file;
        }

        @Override
        public String format(LoggingEvent event)
        {
            if (event == null)
                return null;

            if (event.getLevel() == Level.DEBUG && file)
                return null;

            StringBuilder builder = new StringBuilder(); // StringBuilders are more efficient than simple String concatenations
            builder.append(currentTime());
            builder.append('[').append(logger.getName()).append("] ");
            builder.append(event.getLevel().toString()).append(": ");
            builder.append(event.getMessage());

            if (event.getThrowableInformation() != null)
                builder.append(" Exception: ").append(ExceptionUtils.getStackTrace(event.getThrowableInformation().getThrowable()));

            builder.append("\n");

            return builder.toString();
        }

        @Override
        public boolean ignoresThrowable()
        {
            return false;
        }

        @Override
        public void activateOptions()
        {
            // nothing to do here.
        }

        private static String currentTime()
        {
            StringBuilder builder = new StringBuilder();
            LocalDateTime time = LocalDateTime.now();
            builder.append(time.format(DATE_FORMAT));
            return builder.toString();
        }

    }

}
