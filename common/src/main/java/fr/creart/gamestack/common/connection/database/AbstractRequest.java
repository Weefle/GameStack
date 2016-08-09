/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.connection.database;

import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import fr.creart.gamestack.common.misc.Callback;

/**
 * Represents a request to a {@link Database}
 *
 * @param <V> the requested type of the callback
 * @author Creart
 */
public abstract class AbstractRequest<V> {

    protected RequestType type;
    protected Callback<V> callback;

    /**
     * @param type Request's type
     */
    public AbstractRequest(RequestType type)
    {
        this.type = type;
    }

    public AbstractRequest(@NotNull RequestType type, Callback<V> callback)
    {
        this.type = type;
        if (type == RequestType.QUERY)
            Preconditions.checkNotNull(callback, "callback can't be null");
        this.callback = callback;
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

    public Callback<V> getCallback()
    {
        return callback;
    }

}
