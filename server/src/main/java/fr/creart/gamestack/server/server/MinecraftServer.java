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
public class MinecraftServer extends KeptAlive implements Comparable<MinecraftServer> {

    private static final short TIMEOUT_TIME = 15_000;

    private GameStatus gameStatus;
    private HostServer host;
    private int port;
    private String name;
    private int gameId;
    private short onlinePlayers;
    private short maxPlayers;

    /**
     * @param host          the hosting server
     * @param port          server's port
     * @param name          server's name
     * @param gameId      hosted game's name
     * @param onlinePlayers players currently connected to the server
     * @param maxPlayers    the maximal amount of players
     */
    public MinecraftServer(GameStatus status, HostServer host, int port, String name, int gameId, short onlinePlayers, short maxPlayers)
    {
        this.gameStatus = status;
        this.host = host;
        this.port = port;
        this.name = name;
        this.gameId = gameId;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns current Minecraft server's host
     *
     * @return current Minecraft server's host
     */
    public HostServer getHost()
    {
        return host;
    }

    /**
     * Returns server's used port
     *
     * @return server's used port
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Returns server's name
     *
     * @return server's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns game's id
     *
     * @return game's id
     */
    public int getGameId()
    {
        return gameId;
    }

    /**
     * Returns the current amount of players on the server
     *
     * @return the current amount of players on the server
     */
    public short getOnlinePlayers()
    {
        return onlinePlayers;
    }

    /**
     * Returns the maximal amount of players that the server can host
     *
     * @return the maximal amount of players that the server can host
     */
    public short getMaxPlayers()
    {
        return maxPlayers;
    }

    /**
     * Returns the amount of available slots
     *
     * @return the amount of available slots
     */
    public int getAvailableSlots()
    {
        return maxPlayers - onlinePlayers;
    }

    /**
     * Returns current game's status
     *
     * @return current game's status
     */
    public GameStatus getGameStatus()
    {
        return gameStatus;
    }

    /**
     * Returns <tt>true</tt> if players can join this server and play
     *
     * @return <tt>true</tt> if players can join this server and play
     */
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

    /**
     * Minecraft servers with more slots are higher
     */
    @Override
    public int compareTo(MinecraftServer other)
    {
        /*
        comparator old way
        (first, second) -> first.getAvailableSlots() < second.getAvailableSlots()
                    && first.getAvailableSlots() > 0 ? 1 : -1
         */
        return getAvailableSlots() < other.getAvailableSlots() && getAvailableSlots() > 0 ? 1 : -1;
    }

    @Override
    public int hashCode()
    {
        int ret = 31 * name.hashCode();
        ret += 31 * gameId;
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
