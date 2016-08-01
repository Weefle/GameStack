package fr.creart.gamestack.common.connection.database.sql;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.connection.database.AbstractRequest;
import fr.creart.gamestack.common.connection.database.RequestType;
import fr.creart.gamestack.common.misc.Callback;
import java.sql.ResultSet;

/**
 * Represents a SQL request to the database
 *
 * @author Creart
 */
public class SQLRequest extends AbstractRequest {

    private String request;
    private Callback<ResultSet> queryCallback;

    /**
     * @param type    request's type. If it is a query, please use the other constructor with the callback.
     * @param request the sql request
     */
    public SQLRequest(RequestType type, String request)
    {
        this(type, request, null);
    }

    /**
     * @param type          request's type
     * @param request       the sql request
     * @param queryCallback the callback, called when the query is executed
     */
    public SQLRequest(RequestType type, String request, Callback<ResultSet> queryCallback)
    {
        super(type);
        if (type == RequestType.QUERY)
            Preconditions.checkNotNull(queryCallback, "callback can't be null");
        this.request = request;
        this.queryCallback = queryCallback;
    }

    public String getRequest()
    {
        return request;
    }

    public Callback<ResultSet> getQueryCallback()
    {
        return queryCallback;
    }

    @Override
    public String toString()
    {
        return "SQLRequest{request" + request + ", request_type=" + type.name() + "}";
    }

}
