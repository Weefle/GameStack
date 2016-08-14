/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.server;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a server which can host Minecraft servers.
 *
 * @author Creart
 */
public class HostServer {

    private String address;
    /**
     * Capacity in gamegrams
     */
    private float capacity;
    private float usedCapacity;
    private Map<InetSocketAddress, MinecraftServer> minecraftServers;

    public HostServer(String address, float capacity, float usedCapacity)
    {
        this.address = address;
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
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
    public Map<InetSocketAddress, MinecraftServer> getMinecraftServers()
    {
        if (minecraftServers == null)
            minecraftServers = new HashMap<>();
        return minecraftServers;
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
    }

}
