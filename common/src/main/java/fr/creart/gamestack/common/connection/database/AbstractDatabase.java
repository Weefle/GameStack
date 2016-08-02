package fr.creart.gamestack.common.connection.database;

import fr.creart.gamestack.common.connection.ConnectionContainer;
import fr.creart.gamestack.common.connection.ConnectionData;

/**
 * @author Creart
 */
public abstract class AbstractDatabase<T, Q extends AbstractRequest, CONN_DATA extends ConnectionData>
        extends ConnectionContainer<T, CONN_DATA> implements Database<Q> {

    public AbstractDatabase(int threads)
    {
        super(threads);
    }

}
