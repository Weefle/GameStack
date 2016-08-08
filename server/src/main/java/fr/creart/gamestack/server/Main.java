package fr.creart.gamestack.server;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.command.CommandsManager;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.conf.YamlConfiguration;
import fr.creart.gamestack.common.io.FileUtil;
import fr.creart.gamestack.common.lang.Decimals;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Chrono;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Creart
 */
public class Main {

    public static void main(String[] args)
    {

        try {
            CommonLogger.createLogger("Server");
            CommonLogger.info("Starting up GameStack server...");
            Chrono chrono = new Chrono();

            chrono.markStart(TimeUnit.MILLISECONDS);

            // read the configuration file.
            Validation<Exception, File> saveValidation = FileUtil.saveResource("config.yml", "config.yml", false);

            if (saveValidation.isSuccess()) {
                Configuration configuration = new YamlConfiguration(saveValidation.toOptional().orElse(new File("config.yml")));
                configuration.initialize();
                CommonLogger.info("Loaded the configuration file (config.yml).");
            }

            else {
                CommonLogger.fatal("Could not load the configuration file (config.yml)!", saveValidation.swap().toOptional().get());
                CommonLogger.info("Exiting...");
                Commons.getInstance().close();
                System.exit(1);
                return;
            }

            chrono.markEnd(TimeUnit.MILLISECONDS);

            CommonLogger.info("Done (~" + Decimals.firstDecimals((double) chrono.differenceAs(TimeUnit.MILLISECONDS, TimeUnit.MILLISECONDS) / 1000, 1) + "s.)");
            // listen commands

            CommandsManager.getInstance().listenConsole();
        } catch (Exception e) {
            CommonLogger.fatal("An exception has been encountered during the execution of the program!", e);
            CommonLogger.info("Exiting...");
            System.exit(1);
            return;
        }

        // finally
        System.exit(0);
    }

}
