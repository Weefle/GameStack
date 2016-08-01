package fr.creart.gamestack.common.connection.database.sql;

import fr.creart.gamestack.common.connection.database.AbstractRequest;
import fr.creart.gamestack.common.connection.database.RequestType;

/**
 * @author Creart
 */
public class SQLRequest extends AbstractRequest {

    private String request;

    public SQLRequest(RequestType type, String request)
    {
        super(type);
        this.request = request;
    }

    public String getRequest()
    {
        return request;
    }

}
