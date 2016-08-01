package fr.creart.gamestack.common.misc;

/**
 * A task which is executed when the attended value is received or finally usable.
 *
 * @author Creart
 */
@FunctionalInterface
public interface Callback<V> {

    /**
     * Does whatever it wants with the given value.
     *
     * @param value Value
     */
    void call(V value) throws Exception;

}
