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

package fr.creart.gamestack.server;

import com.google.common.base.Strings;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.command.CommandsManager;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.conf.PropertiesConfiguration;
import fr.creart.gamestack.common.io.FileUtil;
import fr.creart.gamestack.common.lang.Decimals;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Chrono;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author Creart
 */
public class Main {

    private static final String CONFIGURATION_FILE = "server.properties";
    private static final String NETWORK_CONFIGURATION_FILE = "network.properties";

    public static void main(String[] args)
    {
        try {
            CommonLogger.createLogger("Server");
            CommonLogger.info("Starting up GameStack server...");
            Chrono chrono = new Chrono();

            chrono.markStart(TimeUnit.MILLISECONDS);

            Configuration configuration = loadConfiguration(CONFIGURATION_FILE, CONFIGURATION_FILE);

            if (configuration == null)
                exitCantLoad(CONFIGURATION_FILE);

            Configuration networkConf = loadConfiguration(NETWORK_CONFIGURATION_FILE, NETWORK_CONFIGURATION_FILE);

            if (networkConf == null)
                exitCantLoad(NETWORK_CONFIGURATION_FILE);

            configuration.initialize();
            networkConf.initialize();

            StackServer server = StackServer.getInstance();
            server.setConfiguration(configuration);
            server.initialize();

            chrono.markEnd(TimeUnit.MILLISECONDS);

            CommonLogger.info("Done (~" + Decimals.firstDecimals((double) chrono.differenceAs(TimeUnit.MILLISECONDS, TimeUnit.MILLISECONDS) / 1000, 1) + "s.)");

            // listen commands
            CommandsManager instance = CommandsManager.getInstance();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (server.isRunning()) {
                // this is going to change because when the server is not running anymore, it has to wait for a line to exit
                String line = reader.readLine();
                if (!Strings.isNullOrEmpty(line))
                    instance.executeCommand(line, null);
            }
            reader.close();
            StackServer.getInstance().stop();
        } catch (Exception e) {
            CommonLogger.fatal("An exception has been encountered during the execution of the program!", e);
            CommonLogger.fatal("Exiting...");
            System.exit(1);
            return;
        } finally {
            Commons.getInstance().close();
        }

        CommonLogger.info("Thank you for using GameStack. Good-bye!");

        // finally
        System.exit(0);
    }

    private static Configuration loadConfiguration(String fileName, String destination)
    {
        // read the configuration file.
        Validation<Exception, File> saveValidation = FileUtil.saveResource(fileName, destination, false);

        if (saveValidation.isSuccess()) {
            CommonLogger.info("Successfully loaded configuration file (" + fileName + ").");
            return new PropertiesConfiguration(saveValidation.toOptional().get());
        }

        return null;
    }

    private static void exitCantLoad(String file)
    {
        CommonLogger.fatal(String.format("Could not load the configuration file (%s)!", file));
        CommonLogger.info("Exiting...");
        System.exit(1);
    }

}
