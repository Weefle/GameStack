/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.pipeline;

/**
 * Represents one of the actions on the given objects of a {@link Pipeline}.
 *
 * @author Creart
 */
public interface PipelineProvider<T> {

    /**
     * Called as an action when the given object has to/can be treated.
     *
     * @param t the object
     */
    void pipeline(T t);

}
