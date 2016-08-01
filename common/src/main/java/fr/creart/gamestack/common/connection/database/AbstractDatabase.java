package fr.creart.gamestack.common.connection.database;

import fr.creart.gamestack.common.connection.ConnectionContainer;

/**
 * @author Creart
 */
public abstract class AbstractDatabase<T, Q extends AbstractRequest> extends ConnectionContainer<T> implements Database<Q> {

    public AbstractDatabase(int threads)
    {
        super(threads);
    }

}
