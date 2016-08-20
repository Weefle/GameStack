/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.protocol.packet;

import fr.creart.gamestack.common.protocol.packet.result.PlayerTeleport;
import fr.creart.protocolt.bytestreams.ByteArrayDataSource;
import fr.creart.protocolt.bytestreams.ByteArrayDataWriter;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;
import java.util.UUID;

/**
 * Sent when a player needs to be teleported to a specific server
 *
 * @author Creart
 */
public class PlayerTeleportPacket extends ByteArrayPacket<PlayerTeleport> {

    public PlayerTeleportPacket(int id)
    {
        super(id);
    }

    @Override
    public PlayerTeleport read(ByteArrayDataSource source)
    {
        return new PlayerTeleport(UUID.fromString(source.readString()), source.readString());
    }

    @Override
    public void write(ByteArrayDataWriter writer, PlayerTeleport teleport)
    {
        writer.write(teleport.getPlayersUniqueId().toString());
        writer.write(teleport.getTargetServer());
    }

}
