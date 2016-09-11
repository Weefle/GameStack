/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.server;

import fr.creart.gamestack.common.game.GameStatus;
import fr.creart.gamestack.common.misc.KeptAlive;

/**
 * Represents a Minecraft server (which hosts players)
 *
 * @author Creart
 */
public class MinecraftServer extends KeptAlive {

    private static final short TIMEOUT_TIME = 15_000;

    private GameStatus gameStatus;
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
    public MinecraftServer(GameStatus status, HostServer host, int port, String name, String gameName, short onlinePlayers, short maxPlayers)
    {
        this.gameStatus = status;
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

    public GameStatus getGameStatus()
    {
        return gameStatus;
    }

    public boolean isJoinable()
    {
        return maxPlayers > onlinePlayers && gameStatus == GameStatus.LOBBY;
    }

    /**
     * Called when the {@link fr.creart.gamestack.common.protocol.packet.MinecraftServerStatusPacket} is called
     *
     * @param onlinePlayers currently online players
     * @param maxPlayers    maximal amount of players
     */
    public void update(GameStatus status, short onlinePlayers, short maxPlayers)
    {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.gameStatus = status;
        keepAlive();
    }

    @Override
    protected long getAliveDifference()
    {
        return TIMEOUT_TIME;
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
