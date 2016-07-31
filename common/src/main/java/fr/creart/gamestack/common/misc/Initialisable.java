package fr.creart.gamestack.common.misc;

/**
 * Represents an object which can be initialized
 *
 * @author Creart
 */
@FunctionalInterface
public interface Initialisable {

    /**
     * Initializes the object.
     */
    void initialize();

}
