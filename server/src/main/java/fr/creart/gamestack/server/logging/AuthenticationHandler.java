package fr.creart.gamestack.server.logging;

import com.google.common.base.Strings;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.netty.protocol.SimplePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.UUID;

/**
 * @author Creart
 */
public class AuthenticationHandler extends ChannelInboundHandlerAdapter {

    private String authToken;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        SimplePacket packet = (SimplePacket) msg;
        if (Strings.isNullOrEmpty(authToken)) {
            if (packet.getId() != 0)
                ctx.close();
            else {
                String gen = UUID.randomUUID().toString();
                ctx.writeAndFlush(gen);
                authToken = gen;
            }
        } else {
            if (msg.equals(authToken)) {
                ctx.pipeline().addBefore("auth", "file", new LogFileHandler());
                ctx.writeAndFlush("OK");
                ctx.pipeline().remove(this);
            } else
                ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        CommonLogger.error("An exception was encountered with Netty channel (Address: '" + ctx.channel().remoteAddress() + "').", cause);
        CommonLogger.error("Closing the channel.");
        ctx.close();
    }

}
