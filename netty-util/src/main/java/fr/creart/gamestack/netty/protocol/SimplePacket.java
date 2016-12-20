package fr.creart.gamestack.netty.protocol;

/**
 * Represents a simple packet, with an id and data written in a String
 *
 * @author Creart
 */
public class SimplePacket {

    private final short id;
    private final String data;

    /**
     * Default constructor
     *
     * @param id    packet's id
     * @param data  packet's data
     */
    public SimplePacket(short id, String data)
    {
        this.id = id;
        this.data = data;
    }

    /**
     * Returns packet's id
     *
     * @return packet's id
     */
    public short getId()
    {
        return id;
    }

    /**
     * Returns packet's data
     *
     * @return packet's data
     */
    public String getData()
    {
        return data;
    }

}
