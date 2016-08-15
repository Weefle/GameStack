/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.pipeline;

import java.util.Collection;

/**
 * A series of actions which can be registered, a pipeline of actions.
 * To register a provider: {@link #add(PipelineProvider)}
 * To register multiple providers: {@link #addAll(Collection)}
 * To finally call the registered providers: {@link #call(Object)}
 *
 * @author Creart
 */
public interface Pipeline<T> {

    /**
     * Adds a single provider provider
     *
     * @param provider provider to add
     */
    void add(PipelineProvider<T> provider);

    /**
     * Adds all the given providers
     *
     * @param providers providers to add
     */
    void addAll(Collection<PipelineProvider<T>> providers);

    /**
     * Calls all the registered providers with the given object
     *
     * @param object object to call with
     */
    void call(T object);

}
