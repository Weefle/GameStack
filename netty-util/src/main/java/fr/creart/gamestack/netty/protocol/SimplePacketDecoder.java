package fr.creart.gamestack.netty.protocol;

import fr.creart.gamestack.netty.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Decodes {@link SimplePacket}s for Netty
 *
 * @author Creart
 */
public class SimplePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        out.add(new SimplePacket(in.readShort(), NettyUtil.readString(in)));
    }

}
