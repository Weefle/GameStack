package fr.creart.gamestack.common.misc;

/**
 * Represents an object which can be destroyed.
 *
 * @author Creart
 */
@FunctionalInterface
public interface Destroyable {

    /**
     * Destroys the object.
     */
    void destroy();

}
