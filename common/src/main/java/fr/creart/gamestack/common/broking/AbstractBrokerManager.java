package fr.creart.gamestack.common.broking;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fr.creart.gamestack.common.connection.ConnectionContainer;
import fr.creart.gamestack.common.misc.PacketListener;
import fr.creart.gamestack.common.protocol.ProtocolWrap;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * @author Creart
 */
public abstract class AbstractBrokerManager<T> extends ConnectionContainer<T> implements BrokerManager {

    protected Multimap<Integer, PacketListener> listeners = HashMultimap.create();

    public AbstractBrokerManager(int threads)
    {
        super(threads);
    }

    @Override
    public void registerListener(int packetId, PacketListener listener)
    {
        Preconditions.checkArgument(ProtocolWrap.hasPacket(packetId), "packet not declared");
        Preconditions.checkNotNull(listener, "listener should not be null");

        listeners.put(packetId, listener);
    }

    @Override
    public <P> void publish(ByteArrayPacket<P> packet, P output)
    {
        Preconditions.checkNotNull(packet, "packet can't be null");
        Preconditions.checkNotNull(output, "output can't be null");
    }

}
