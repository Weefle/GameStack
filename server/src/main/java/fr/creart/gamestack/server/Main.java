package fr.creart.gamestack.server;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.conf.YamlConfiguration;
import fr.creart.gamestack.common.io.FileUtil;
import fr.creart.gamestack.common.lang.Decimals;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Chrono;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Creart
 */
public class Main {

    public static void main(String[] args)
    {
        CommonLogger.createLogger("Server");
        CommonLogger.info("Starting up GameStack server...");
        Commons.initialize("Server");
        Chrono chrono = new Chrono();

        chrono.markStart(null);

        // read the configuration file.
        Validation<Exception, File> saveValidation = FileUtil.saveResource("config.yml", "config.yml", false);

        if (saveValidation.isSuccess()) {
            Configuration configuration = new YamlConfiguration(saveValidation.toOptional().orElse(new File("config.yml")));
            configuration.initialize();
            CommonLogger.info("Loaded the configuration file (config.yml).");
        }
        else {
            CommonLogger.fatal("Could not load the configuration file (config.yml)!");
            if (saveValidation.swap().toOptional().orElse(new Exception("Could not get exception.")) instanceof FileNotFoundException)
                CommonLogger.fatal("The configuration file could not be found.");
            else
                CommonLogger.fatal("Could not save the file. Please give the necessary permissions to the user which is running the GameStack server.");

            CommonLogger.error("Shutting down the server...");
            Commons.close();
            return;
        }

        chrono.markEnd(null);

        CommonLogger.info("Done (~" + Decimals.firstDecimals(((double) chrono.difference()) / 1000, 1) + "s).");
    }

}
