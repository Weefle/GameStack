/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.server;

/**
 * Represents a Minecraft server (which hosts players)
 *
 * @author Creart
 */
public class MinecraftServer {

    private HostServer host;
    private int port;
    private String name;
    private String gameName;
    private short onlinePlayers;
    private short maxPlayers;

    /**
     * @param host          the hosting server
     * @param port          server's port
     * @param name          server's name
     * @param gameName      hosted game's name
     * @param onlinePlayers players currently connected to the server
     * @param maxPlayers    the maximal amount of players
     */
    public MinecraftServer(HostServer host, int port, String name, String gameName, short onlinePlayers, short maxPlayers)
    {
        this.host = host;
        this.port = port;
        this.name = name;
        this.gameName = gameName;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    public HostServer getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public String getName()
    {
        return name;
    }

    public String getGameName()
    {
        return gameName;
    }

    public short getOnlinePlayers()
    {
        return onlinePlayers;
    }

    public short getMaxPlayers()
    {
        return maxPlayers;
    }

    public int getAvailableSlots()
    {
        return maxPlayers - onlinePlayers;
    }

    public void update(short onlinePlayers, short maxPlayers)
    {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int hashCode()
    {
        int ret = 31 * name.hashCode();
        ret += 31 * gameName.hashCode();
        ret += host.getAddress().hashCode();
        ret += port;
        return ret;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof MinecraftServer && obj.hashCode() == hashCode();
    }

}
