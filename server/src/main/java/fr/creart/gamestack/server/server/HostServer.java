/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.server;

import fr.creart.gamestack.common.misc.KeptAlive;
import fr.creart.gamestack.common.pipeline.PipelineProvider;
import fr.creart.gamestack.common.protocol.packet.result.MinecraftServerUpdate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a server which can host Minecraft servers.
 *
 * @author Creart
 */
public class HostServer extends KeptAlive implements PipelineProvider<Collection<MinecraftServer>> {

    private static final short TIMEOUT_TIME = 15_000;

    private String address;
    /**
     * Capacity in gamegrams
     */
    private float capacity;
    private float usedCapacity;
    private Map<Integer, MinecraftServer> minecraftServers;

    /**
     * @param address      server's address
     * @param capacity     server's max capacity
     * @param usedCapacity server's used capacity
     */
    public HostServer(String address, float capacity, float usedCapacity)
    {
        this.address = address;
        update(capacity, usedCapacity);
    }

    /**
     * Returns the server's address
     *
     * @return the server's address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Returns server's total capacity
     *
     * @return server's total capacity
     */
    public float getCapacity()
    {
        return capacity;
    }

    /**
     * Returns server's used capacity
     *
     * @return server's used capacity
     */
    public float getUsedCapacity()
    {
        return usedCapacity;
    }

    /**
     * Returns the map of Minecraft servers (creates it if it hasn't been yet)
     *
     * @return the map of Minecraft servers
     */
    private Map<Integer, MinecraftServer> getMinecraftServers()
    {
        if (minecraftServers == null)
            minecraftServers = new HashMap<>();
        return minecraftServers;
    }

    /**
     * Returns the hosted Minecraft servers
     *
     * @return the hosted Minecraft servers
     */
    public Collection<MinecraftServer> getHostedServers()
    {
        return getMinecraftServers().values();
    }

    /**
     * Updates Minecraft server
     *
     * @param port   server's port
     * @param update server's update
     */
    public void updateMinecraftServer(int port, MinecraftServerUpdate update)
    {
        MinecraftServer server = getMinecraftServers().get(port);
        if (server == null) {
            server = new MinecraftServer(update.getGameStatus(),
                    this,
                    update.getPort(),
                    update.getAddress(),
                    update.getGameName(),
                    update.getOnlinePlayers(),
                    update.getMaxPlayers());
            minecraftServers.put(server.getPort(), server);
        }
        else
            server.update(update.getGameStatus(), update.getOnlinePlayers(), update.getMaxPlayers());
    }

    /**
     * Returns the removed server
     *
     * @param port server's port
     * @return the removed server
     */
    public MinecraftServer removeMinecraftServer(int port)
    {
        return getMinecraftServers().remove(port);
    }

    /**
     * Returns <tt>true</tt> if the current host server has some running Minecraft servers
     *
     * @return <tt>true</tt> if the current host server has some running Minecraft servers
     */
    public boolean hasMinecraftServers()
    {
        return minecraftServers != null && minecraftServers.size() > 0;
    }

    /**
     * Updates host server's information
     *
     * @param capacity     the server total capacity
     * @param usedCapacity the server used capacity
     */
    public void update(float capacity, float usedCapacity)
    {
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
        keepAlive();
    }

    @Override
    protected long getAliveDifference()
    {
        return TIMEOUT_TIME;
    }

    @Override
    public void pipeline(Collection<MinecraftServer> srvs)
    {
        srvs.addAll(minecraftServers.values());
    }

}
