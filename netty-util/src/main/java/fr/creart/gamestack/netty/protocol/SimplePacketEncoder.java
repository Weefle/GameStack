package fr.creart.gamestack.netty.protocol;

import fr.creart.gamestack.netty.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encodes {@link SimplePacket}s for Netty
 *
 * @author Creart
 */
public class SimplePacketEncoder extends MessageToByteEncoder<SimplePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SimplePacket msg, ByteBuf out) throws Exception
    {
        out.writeShort(msg.getId());
        NettyUtil.writeString(out, msg.getData());
    }

}
