package fr.creart.gamestack.common.protocol;

import fr.creart.protocolt.ProtoColt;
import fr.creart.protocolt.Protocol;
import fr.creart.protocolt.bytestreams.ByteArrayPacket;

/**
 * Wraps {@link fr.creart.protocolt.ProtoColt} library.
 *
 * @author Creart
 */
public final class ProtocolWrap {

    private static Protocol protocol;

    static {
        ProtoColt.configure(true);
        ProtoColt proto = ProtoColt.getInstance();
        protocol = proto.getOrCreateProtocol("gamestack");
        // declare packets
    }

    private ProtocolWrap()
    {

    }

    /**
     * Returns the packet associated to the id
     *
     * @param id id of the packet
     * @return the packet associated to the id
     */
    @SuppressWarnings("unchecked")
    public static <T> ByteArrayPacket<T> getPacketById(int id)
    {
        return (ByteArrayPacket<T>) protocol.getPacketById(id);
    }

}
