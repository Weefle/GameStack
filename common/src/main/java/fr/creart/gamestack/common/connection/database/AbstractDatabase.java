/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.database;

import fr.creart.gamestack.common.connection.ConnectionContainer;

/**
 * {@inheritDoc}
 * @param <Q> handled requests
 * @author Creart
 */
public abstract class AbstractDatabase<T, Q extends AbstractRequest<?>, D extends DatabaseConnectionData>
        extends ConnectionContainer<T, D> implements Database<Q> {

    /**
     * {@inheritDoc}
     */
    public AbstractDatabase(int threads)
    {
        super(threads);
    }

}
