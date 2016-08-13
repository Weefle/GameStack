/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

/**
 * Represents a Wrapper
 *
 * @param <T> the wrapped value
 * @author Creart
 */
public abstract class Wrapper<T> {

    protected T value;

    /**
     * Default constructor, null value by default
     */
    public Wrapper()
    {
        this(null);
    }

    /**
     * @param value wrapper's value
     */
    public Wrapper(T value)
    {
        this.value = value;
    }

    /**
     * Changes the current value to the new one
     *
     * @param value The new value
     */
    public abstract void set(T value);

    /**
     * Returns the current value
     *
     * @return the current value
     */
    public abstract T get();

}
