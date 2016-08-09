/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.command.CommandsManager;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.connection.rmq.RabbitConnectionData;
import fr.creart.gamestack.common.connection.rmq.RabbitContainer;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Initialisable;
import fr.creart.gamestack.server.command.StopCommand;
import fr.creart.gamestack.server.conf.ConfigurationConstants;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Creart
 */
public class StackServer implements Initialisable {

    private static StackServer instance;

    private Configuration configuration;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private volatile boolean running;

    private ThreadGroup softGroup;

    private StackServer()
    {
        // no instance
    }

    void setConfiguration(Configuration configuration)
    {
        if (this.configuration == null)
            this.configuration = configuration;
    }

    @Override
    public void initialize()
    {
        Commons commons = Commons.getInstance();
        commons.initialize("Server");
        softGroup = commons.getThreadsManager().newThreadGroup("Server");

        commons.connect(new RabbitConnectionData(configuration.getString(ConfigurationConstants.BROKER_USERNAME, "root"),
                        configuration.getString(ConfigurationConstants.BROKER_PASSWORD, "root"),
                        configuration.getString(ConfigurationConstants.BROKER_HOST, "192.168.99.100"),
                        configuration.getInteger(ConfigurationConstants.BROKER_PORT, 5672),
                        configuration.getString(ConfigurationConstants.BROKER_VIRTUAL_HOST, "")),
                new RabbitContainer(3), (byte) 3);

        // registering commands
        CommandsManager.getInstance().registerCommand(new StopCommand());

        // finally
        running = true;
    }

    /**
     * Stops the stack server
     */
    public void stop()
    {
        if (!isRunning())
            return;

        new Thread(softGroup, () -> {
            CommonLogger.info("Shutting down GameStack server...");

            running = false;

            CommonLogger.info("Finished GameStack's shutdown.");
        }, "Stop Thread").start();
    }

    /**
     * Returns <code>true</code> if the server is currently running
     *
     * @return <code>true</code> if the server is currently running
     */
    public boolean isRunning()
    {
        return running;
    }

    /**
     * Returns the thread group used by the server
     *
     * @return the thread group used by the server
     */
    public ThreadGroup getSoftGroup()
    {
        return softGroup;
    }

    /**
     * Returns the single instance of the StackServer class
     *
     * @return the single instance of the StackServer class
     */
    public static StackServer getInstance()
    {
        if (instance == null)
            instance = new StackServer();
        return instance;
    }

}
