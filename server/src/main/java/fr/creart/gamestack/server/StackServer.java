/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.command.CommandsManager;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.connection.database.DatabaseConnectionData;
import fr.creart.gamestack.common.connection.rmq.RabbitConnectionData;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.DependsManager;
import fr.creart.gamestack.common.misc.Initialisable;
import fr.creart.gamestack.common.protocol.packet.result.HostUpdate;
import fr.creart.gamestack.server.command.StopCommand;
import fr.creart.gamestack.server.conf.ConfigurationConstants;
import fr.creart.gamestack.server.listener.HostUpdateListener;
import fr.creart.gamestack.server.server.HostServer;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String, HostServer> servers = new HashMap<>();

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
        String brokerSystem = configuration.getString(ConfigurationConstants.BROKER_SYSTEM, "rabbitmq");
        String databaseSystem = configuration.getString(ConfigurationConstants.DATABASE_SYSTEM, "mysql");
        DependsManager dependsManager = new DependsManager();
        dependsManager.createAssociations();

        commons.connect(new RabbitConnectionData(configuration.getString(ConfigurationConstants.BROKER_USERNAME, "root"),
                        configuration.getString(ConfigurationConstants.BROKER_PASSWORD, "root"),
                        configuration.getString(ConfigurationConstants.BROKER_HOST, "192.168.99.100"),
                        configuration.getInteger(ConfigurationConstants.BROKER_PORT, 5672),
                        configuration.getString(ConfigurationConstants.BROKER_VIRTUAL_HOST, "")),
                dependsManager.getAssociation(brokerSystem, 3),
                new DatabaseConnectionData(configuration.getString(ConfigurationConstants.DATABASE_USERNAME, "root"),
                        configuration.getString(ConfigurationConstants.DATABASE_PASSWORD, "root"),
                        configuration.getString(ConfigurationConstants.DATABASE_HOST, "192.168.99.100"),
                        configuration.getInteger(ConfigurationConstants.DATABASE_PORT, 5432),
                        configuration.getString(ConfigurationConstants.DATABASE_DB, "gamestack")),
                dependsManager.getAssociation(databaseSystem, 3),
                (byte) 3
        );

        // registering commands
        CommandsManager.getInstance().registerCommand(new StopCommand());

        // registering packet listeners
        commons.getMessageBroker().registerListener(0x01, new HostUpdateListener());

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
     * If no server has been saved for the given address, a new one is created.
     * Otherwise, its data is updated.
     *
     * @param update the update to apply
     */
    public void updateServer(HostUpdate update)
    {
        lock.writeLock().lock();
        try {
            HostServer server = servers.get(update.getAddress());
            if (server == null) {
                server = new HostServer(update.getAddress(), update.getCapacity(), update.getUsedCapacity());
                servers.put(server.getAddress(), server);
            }
            else
                server.update(update.getCapacity(), update.getUsedCapacity());
        } finally {
            lock.writeLock().unlock();
        }
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
