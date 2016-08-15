/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet.result;

/**
 * Represents a Minecraft server update, which can be an adding, update or deletion.
 *
 * @author Creart
 */
public class MinecraftServerUpdate extends SocketHostData {

    private KeepAliveStatus status;
    private String gameName;
    private short onlinePlayers;
    private short maxPlayers;

    /**
     * @param address server's address
     * @param port    server's port
     * @param status  server's status
     */
    public MinecraftServerUpdate(String address, String gameName, int port, KeepAliveStatus status)
    {
        super(address, port);
        this.gameName = gameName;
        this.status = status;
    }

    public void setOnlinePlayers(short onlinePlayers)
    {
        this.onlinePlayers = onlinePlayers;
    }

    public void setMaxPlayers(short maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public String getGameName()
    {
        return gameName;
    }

    public KeepAliveStatus getStatus()
    {
        return status;
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
