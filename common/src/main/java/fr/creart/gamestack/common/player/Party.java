/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.player;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.gamestack.common.protocol.packet.result.MessageResult;
import fr.creart.gamestack.common.protocol.packet.result.PlayerTeleport;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * @author Creart
 */
public class Party implements Queueable {

    private static final ByteArrayPacket<PlayerTeleport> TELEPORT_PACKET = ProtocolWrap.getPacketById(ProtocolWrap.PLAYER_TELEPORT_PACKET_ID);
    private static final ByteArrayPacket<MessageResult> MESSAGE_PACKET = ProtocolWrap.getPacketById(ProtocolWrap.CHAT_MESSAGE_PACKET_ID);

    private final Player leader;
    private Player[] players;

    public Party(Player leader, Player[] players)
    {
        this.leader = leader;
        this.players = players;
    }

    @Override
    public void connect(String serverName)
    {
        Commons.getInstance().getMessageBroker().publish(TELEPORT_PACKET, new PlayerTeleport(serverName, getAllUUIDs()));
    }

    @Override
    public void sendMessage(String path, MessageType type)
    {
        Commons.getInstance().getMessageBroker().publish(MESSAGE_PACKET, new MessageResult(type, getAllUUIDs(), path));
    }

    @Override
    public byte getWeight()
    {
        // 1 corresponds to the leader
        return (byte) (1 + players.length);
    }

    @Override
    public byte getPriority()
    {
        return leader.getPriority();
    }

    @Override
    public int hashCode()
    {
        return leader.hashCode();
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public Player getLeader()
    {
        return leader;
    }

    private String[] getAllUUIDs()
    {
        String[] uuids = new String[players.length + 1];
        uuids[0] = leader.getUniqueId();
        for (int i = 0; i < players.length; i++)
            uuids[i + 1] = players[i].getUniqueId();
        return uuids;
    }

}
