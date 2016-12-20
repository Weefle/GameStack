package fr.creart.gamestack.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadFactory;

/**
 * @author Creart
 */
public class NettyUtil {

    private NettyUtil()
    {
        // no instance
    }

    /**
     * Creates an {@link EpollEventLoopGroup} if {@link Epoll} is available,
     * otherwise creates a {@link NioEventLoopGroup}
     *
     * @param threads   number of threads
     * @param group     the thread group
     * @return a new {@link EpollEventLoopGroup} or {@link NioEventLoopGroup}
     */
    public static EventLoopGroup createEventLoopGroup(int threads, ThreadGroup group)
    {
        ThreadFactory factory = (runnable) -> new Thread(group, runnable);
        return Epoll.isAvailable() ? new EpollEventLoopGroup(threads, factory) : new NioEventLoopGroup(threads, factory);
    }

    /**
     * Reads a String from the given {@link ByteBuf}
     *
     * @param buffer    the buffer
     * @return a String read from the given {@link ByteBuf}
     */
    public static String readString(ByteBuf buffer)
    {
        if (buffer == null)
            throw new IllegalArgumentException("buffer cannot be null");
        if (!buffer.isReadable())
            throw new IllegalArgumentException("buffer has to be readable");

        byte[] b = new byte[buffer.readInt()];
        buffer.readBytes(b);
        return new String(b, StandardCharsets.UTF_8);
    }

    /**
     * Writes a String to the given {@link ByteBuf}
     *
     * @param buffer    the buffer
     * @param string    the string to write
     */
    public static void writeString(ByteBuf buffer, String string)
    {
        if (buffer == null)
            throw new IllegalArgumentException("buffer cannot be null");
        if (!buffer.isWritable())
            throw new IllegalArgumentException("buffer has to be writable");

        buffer.writeInt(string.length());
        buffer.writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

}
