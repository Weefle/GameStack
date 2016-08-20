/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.pipeline;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * Default and simple implementation of the {@link Pipeline} interface.
 *
 * @author Creart
 */
public class SimplePipeline<T> implements Pipeline<T> {

    private Set<PipelineProvider<T>> providers = new HashSet<>();

    @Override
    public void addAll(Collection<PipelineProvider<T>> add)
    {
        Validate.notEmpty(add, "add can't be null or empty");
        add.stream().forEach(this::add);
    }

    @Override
    public void add(PipelineProvider<T> provider)
    {
        Validate.notNull(provider, "provider can't be null");
        providers.add(provider);
    }

    @Override
    public void remove(PipelineProvider<T> provider)
    {
        providers.remove(provider);
    }

    @Override
    public void call(T object)
    {
        providers.stream().forEach(provider -> provider.pipeline(object));
    }

}
