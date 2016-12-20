/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.logging;

import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Initialisable;
import fr.creart.gamestack.netty.NettyUtil;
import fr.creart.gamestack.netty.protocol.SimplePacketDecoder;
import fr.creart.gamestack.netty.protocol.SimplePacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Server which listens for logs from distant servers, usually sent before they are closed.
 * Centralises logs.
 *
 * @author Creart
 */
public class LoggingServer implements Initialisable, AutoCloseable {

    /*
    PROTOCOL
    ID 0 => AUTHENTICATION REQUEST
    ID 1 => AUTHENTICATION
    ID 2 => PART OF FILE
    ID 3 => EOF
     */

    private ThreadGroup loggingGroup;
    private Channel channel;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public LoggingServer(ThreadGroup parent)
    {
        loggingGroup = new ThreadGroup(parent, "Logging Server");
    }

    @Override
    public void initialise()
    {
        try {
            boss = NettyUtil.createEventLoopGroup(3, loggingGroup);
            worker = NettyUtil.createEventLoopGroup(3, loggingGroup);
            ServerBootstrap s = new ServerBootstrap()
                    .group(boss, worker)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_TIMEOUT, 10000)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception
                        {
                            channel.pipeline().addLast(
                                    new SimplePacketDecoder(),
                                    new LineBasedFrameDecoder(8192),
                                    new SimplePacketEncoder()
                            );
                            channel.pipeline().addLast("auth", new AuthenticationHandler());
                        }
                    });
            channel = s.bind(28).sync().channel();
            CommonLogger.info("The logging server is listening on port 28.");
        } catch (Exception e) {
            CommonLogger.fatal("Could not initialise Netty logging server!", e);
        }
    }

    @Override
    public void close()
    {
        try {
            if (!boss.isShutdown())
                boss.shutdownGracefully();
            if (!worker.isShutdown())
                worker.shutdownGracefully();

            if (channel.isOpen())
                channel.close().sync();
        } catch (Exception e) {
            CommonLogger.error("Could not correctly close logging server.", e);
        }
    }

}
