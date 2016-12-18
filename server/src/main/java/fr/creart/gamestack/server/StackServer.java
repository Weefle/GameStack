/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.conf.Configuration;
import fr.creart.gamestack.common.connection.database.DatabaseConnectionData;
import fr.creart.gamestack.common.connection.rmq.RabbitConnectionData;
import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.common.lang.BasicWrapper;
import fr.creart.gamestack.common.lang.Wrapper;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.DependsManager;
import fr.creart.gamestack.common.misc.Initialisable;
import fr.creart.gamestack.common.pipeline.Pipeline;
import fr.creart.gamestack.common.pipeline.SimplePipeline;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.gamestack.common.protocol.packet.result.HostUpdate;
import fr.creart.gamestack.server.command.CommandsManager;
import fr.creart.gamestack.server.command.StopCommand;
import fr.creart.gamestack.server.conf.ConfigurationConstants;
import fr.creart.gamestack.server.listener.EnqueueListener;
import fr.creart.gamestack.server.listener.HostUpdateListener;
import fr.creart.gamestack.server.listener.MinecraftUpdateListener;
import fr.creart.gamestack.server.listener.PullQueueListener;
import fr.creart.gamestack.server.queue.QueuesManager;
import fr.creart.gamestack.server.server.HostServer;
import fr.creart.gamestack.server.server.MinecraftServer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Creart
 */
public class StackServer implements Initialisable {

    private static StackServer instance = new StackServer();

    private Configuration networkConfiguration;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile boolean running;
    private ThreadGroup softGroup;

    private Map<String, HostServer> servers = new HashMap<>();
    private Pipeline<Collection<MinecraftServer>> minecraftServersPipeline = new SimplePipeline<>();

    private QueuesManager queuesManager = new QueuesManager();

    private StackServer()
    {
        // no instance
    }

    void setNetworkConfiguration(Configuration networkConfiguration)
    {
        if (this.networkConfiguration == null)
            this.networkConfiguration = networkConfiguration;
    }

    @Override
    public void initialize()
    {
        Commons commons = Commons.getInstance();
        commons.initialize("Server");
        softGroup = commons.getThreadsManager().newThreadGroup("Server");
        String brokerSystem = networkConfiguration.getString(ConfigurationConstants.BROKER_SYSTEM, "rabbitmq");
        String databaseSystem = networkConfiguration.getString(ConfigurationConstants.DATABASE_SYSTEM, "mysql");
        DependsManager dependsManager = new DependsManager();
        dependsManager.createAssociations();

        commons.connect(new RabbitConnectionData(networkConfiguration.getString(ConfigurationConstants.BROKER_USERNAME, "root"),
                        networkConfiguration.getString(ConfigurationConstants.BROKER_PASSWORD, "root"),
                        networkConfiguration.getString(ConfigurationConstants.BROKER_HOST, "192.168.99.100"),
                        networkConfiguration.getInteger(ConfigurationConstants.BROKER_PORT, 5672),
                        networkConfiguration.getString(ConfigurationConstants.BROKER_VIRTUAL_HOST, "")),
                dependsManager.getAssociation(brokerSystem, 3),
                new DatabaseConnectionData(networkConfiguration.getString(ConfigurationConstants.DATABASE_USERNAME, "root"),
                        networkConfiguration.getString(ConfigurationConstants.DATABASE_PASSWORD, "root"),
                        networkConfiguration.getString(ConfigurationConstants.DATABASE_HOST, "192.168.99.100"),
                        networkConfiguration.getInteger(ConfigurationConstants.DATABASE_PORT, 5432),
                        networkConfiguration.getString(ConfigurationConstants.DATABASE_DB, "gamestack")),
                dependsManager.getAssociation(databaseSystem, 3),
                (byte) 3
        );

        // registering commands
        CommandsManager.getInstance().registerCommand(new StopCommand());

        // registering packet listeners
        commons.getMessageBroker().registerListener(ProtocolWrap.HOST_UPDATE_PACKET_ID, new HostUpdateListener());
        commons.getMessageBroker().registerListener(ProtocolWrap.MINECRAFT_SERVER_STATUS_PACKET_ID, new MinecraftUpdateListener());
        commons.getMessageBroker().registerListener(ProtocolWrap.PULL_QUEUE_PACKET_ID, new PullQueueListener());
        commons.getMessageBroker().registerListener(ProtocolWrap.ENQUEUE_PACKET_ID, new EnqueueListener());

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
     * Returns the host server associated to the given address
     *
     * @param address server's address
     * @return the host server associated to the given address
     */
    public HostServer getServer(String address)
    {
        lock.readLock().lock();
        try {
            return servers.get(address);
        } finally {
            lock.readLock().unlock();
        }
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
                minecraftServersPipeline.add(server);
            }
            else
                server.update(update.getCapacity(), update.getUsedCapacity());
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes the server associated to the given address and returns it (may be <tt>null</tt>)
     *
     * @param address server's address
     * @return the removed server
     */
    public HostServer removeServer(String address)
    {
        lock.writeLock().lock();
        try {
            HostServer server = servers.remove(address);
            minecraftServersPipeline.remove(server);
            return server;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Returns the server the most available for hosting (the one with the most available resources).
     * <tt>null</tt> if nothing hasn't been found
     *
     * @return the server the most available for hosting
     */
    public HostServer getBestServer()
    {
        lock.readLock().lock();
        try {
            Wrapper<HostServer> best = new BasicWrapper<>();
            servers.values().stream().filter(server -> server != null && !server.hasTimedOut()).forEach(server -> {
                HostServer currentBest = best.get();
                if (currentBest == null || server.getCapacity() - server.getUsedCapacity() > currentBest.getCapacity() - currentBest.getUsedCapacity())
                    best.set(server);
            });
            return best.get();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Returns the best Minecraft server for the given map (null if not found).
     * Sorted by the current strategy which is: first almost full servers.
     *
     * @param map map to look for
     * @return the best server for the given map
     */
    public MinecraftServer getBestMinecraftServer(GameMap map)
    {
        Preconditions.checkNotNull(map, "map can't be null");

        lock.readLock().lock();
        try {
            SortedSet<MinecraftServer> mcServers = new TreeSet<>((first, second) -> first.getAvailableSlots() < second.getAvailableSlots()
                    && first.getAvailableSlots() > 0 ? 1 : -1); // the almost full servers first
            minecraftServersPipeline.call(mcServers);
            return mcServers.first();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Returns the queues manager
     *
     * @return the queues manager
     */
    public QueuesManager getQueuesManager()
    {
        return queuesManager;
    }

    /**
     * Returns the single instance of the StackServer class
     *
     * @return the single instance of the StackServer class
     */
    public static StackServer getInstance()
    {
        return instance;
    }

}
