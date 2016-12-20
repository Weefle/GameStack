package fr.creart.gamestack.server.logging;

import com.google.common.base.Charsets;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.netty.protocol.SimplePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Rewrites the logs line per line
 *
 * @author Creart
 */
public class LogFileHandler extends ChannelInboundHandlerAdapter {

    private BufferedWriter output;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception
    {
        SimplePacket packet = (SimplePacket) o;

        if (output == null) {
            output = Files.newBufferedWriter(Paths.get("logs/client/"), Charsets.UTF_8);
            // TODO: 20/12/2016 define the log file
            // should be => logs/client/host_server_name/mc_server_name.log
            // mc_server_name is actually the game + the number of games played of the game
            // but what is could be host_server_name?
        }

        if (packet.getId() == 3) {
            output.close();
            CommonLogger.info("Done saving logs of '" + ctx.channel().remoteAddress() + "'.");
            ctx.close();
        } else
            output.write(packet.getData());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        CommonLogger.error(
                "An exception has been encountered during a file transfer with '" + ctx.channel().remoteAddress() + "'. Closing the channel",
                cause
        );
    }

}
