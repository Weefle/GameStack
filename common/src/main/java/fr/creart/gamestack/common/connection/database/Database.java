package fr.creart.gamestack.common.connection.database;

/**
 * Represents a database connection
 *
 * @author Creart
 */
public interface Database<T extends AbstractRequest> {

    /**
     * Executes the given request
     *
     * @param request The request
     */
    void executeRequest(T request);

}
