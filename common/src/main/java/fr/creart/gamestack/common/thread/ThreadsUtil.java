package fr.creart.gamestack.common.thread;

import java.util.concurrent.ThreadFactory;

/**
 * Some threads utils.
 *
 * @author Creart
 */
public class ThreadsUtil {

    private static final ThreadGroup PARENT_GROUP = new ThreadGroup("GameStack");
    private static ThreadGroup currentGroup;

    /**
     * Changes the current group for the soft
     *
     * @param name Thread group's name
     */
    public static void setCurrentGroup(String name)
    {
        ThreadsUtil.currentGroup = new ThreadGroup(PARENT_GROUP, name);
    }

    /**
     * Returns the currently held group
     *
     * @return the currently held group
     */
    public static ThreadGroup getCurrentGroup()
    {
        return currentGroup;
    }

    /**
     * Returns a thread factory
     *
     * @param newGroup Thread factory's thread group
     * @return a thread factory
     */
    public static ThreadFactory createFactory(String newGroup)
    {
        return runnable -> new Thread(new ThreadGroup(currentGroup, newGroup), runnable);
    }

}
