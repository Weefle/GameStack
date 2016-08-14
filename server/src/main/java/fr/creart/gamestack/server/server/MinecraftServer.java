/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.server;

import java.net.InetSocketAddress;

/**
 * @author Creart
 */
public class MinecraftServer {

    private InetSocketAddress address;
    private String name;
    private String gameName;
    private short onlinePlayers;
    private short maxPlayers;

    public MinecraftServer(InetSocketAddress address, String name, String gameName, short onlinePlayers, short maxPlayers)
    {
        this.address = address;
        this.name = name;
        this.gameName = gameName;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    public InetSocketAddress getAddress()
    {
        return address;
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

}
