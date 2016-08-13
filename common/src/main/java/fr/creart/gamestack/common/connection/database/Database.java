/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.database;

/**
 * Represents a database connection
 *
 * @param <T> type of requests
 * @author Creart
 */
@FunctionalInterface
public interface Database<T extends AbstractRequest<?>> {

    /**
     * Executes the given request(s)
     *
     * @param requests The request(s)
     */
    void executeRequests(T... requests);

}
