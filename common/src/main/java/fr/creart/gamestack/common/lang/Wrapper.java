package fr.creart.gamestack.common.lang;

/**
 * Represents a Wrapper
 *
 * @param <T> the wrapped value
 *
 * @author Creart
 */
public abstract class Wrapper<T> {

    /**
     * Changes the current value to the new one
     * @param value The new value
     */
    public abstract void set(T value);

    /**
     * Returns the current value
     * @return the current value
     */
    public abstract T get();

}
