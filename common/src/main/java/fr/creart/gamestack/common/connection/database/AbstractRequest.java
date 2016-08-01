package fr.creart.gamestack.common.connection.database;

/**
 * Represents a request to a {@link Database}
 *
 * @author Creart
 */
public abstract class AbstractRequest {

    protected RequestType type;

    /**
     * @param type Request's type
     */
    public AbstractRequest(RequestType type)
    {
        this.type = type;
    }

    /**
     * Returns the request's type
     *
     * @return the request's type
     */
    public final RequestType getType()
    {
        return type;
    }

}
