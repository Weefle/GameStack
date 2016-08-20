/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server;

import com.google.common.base.Strings;
import fr.creart.gamestack.common.app.Application;
import fr.creart.gamestack.common.command.CommandsManager;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.conf.ConfigurationLoadException;
import fr.creart.gamestack.common.conf.PropertiesConfiguration;
import fr.creart.gamestack.common.io.FileUtil;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.log.CommonLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author Creart
 */
public class Main extends Application {

    private static final String CONFIGURATION_FILE = "gamestack.properties";
    private static final String NETWORK_CONFIGURATION_FILE = "network.properties";

    public static void main(String[] args)
    {
        new Main().startup("GameStack Server", "Server");
    }

    @Override
    protected void load() throws Exception
    {
        Configuration configuration = loadConfiguration(CONFIGURATION_FILE, CONFIGURATION_FILE);

        if (configuration == null)
            throw new ConfigurationLoadException("Could not configuration file (" + CONFIGURATION_FILE + ")!");

        Configuration networkConf = loadConfiguration(NETWORK_CONFIGURATION_FILE, NETWORK_CONFIGURATION_FILE);

        if (networkConf == null)
            throw new ConfigurationLoadException("Could not configuration file (" + NETWORK_CONFIGURATION_FILE + ")!");

        configuration.initialize();
        networkConf.initialize();

        StackServer server = StackServer.getInstance();
        server.setNetworkConfiguration(networkConf);
        server.initialize();
    }

    @Override
    public void run() throws Exception
    {
        StackServer server = StackServer.getInstance();
        // listen commands
        CommandsManager instance = CommandsManager.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (server.isRunning()) {
            // this is going to change because when the server is not running anymore, it has to wait for a line to exit
            String line;
            try {
                line = reader.readLine();
            } catch (Exception e) {
                continue; // ignore
            }

            if (!Strings.isNullOrEmpty(line))
                instance.executeCommand(line, null);
        }
        reader.close();
        StackServer.getInstance().stop();
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

}
