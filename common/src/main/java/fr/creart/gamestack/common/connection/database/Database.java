package fr.creart.gamestack.common.connection.database;

/**
 * Represents a database connection
 *
 * @author Creart
 */
public interface Database<T extends AbstractRequest> {

    /**
     * Executes the given request(s)
     *
     * @param requests The request(s)
     */
    void executeRequests(T... requests);

}
