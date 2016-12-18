/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.bungee.listener;

import com.google.common.base.Strings;
import fr.creart.gamestack.common.protocol.PacketListener;
import fr.creart.gamestack.common.protocol.packet.result.MessageResult;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * @author Creart
 */
@SuppressWarnings("deprecation")
public class MessagePacketListener implements PacketListener<MessageResult> {

    @Override
    public void handlePacket(int packetId, MessageResult result)
    {
        ProxyServer server = ProxyServer.getInstance();

        if (!Strings.isNullOrEmpty(result.getMessage()))
            Arrays.stream(result.getPlayerUUIDs()).map(uuid -> server.getPlayer(UUID.fromString(uuid))).filter(Objects::nonNull)
                    .forEach(player -> {
                        switch (result.getType()) {
                            case ACTION_BAR:
                                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(result.getMessage()));
                                break;
                            case TITLE:
                                player.sendTitle(ProxyServer.getInstance().createTitle().subTitle(new TextComponent(result.getMessage())));
                                break;
                            case CHAT_MESSAGE:
                            default:
                                player.sendMessage(result.getMessage());
                        }
                    });
    }

}
