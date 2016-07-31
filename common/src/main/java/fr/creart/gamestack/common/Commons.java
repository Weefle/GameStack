package fr.creart.gamestack.common;

import fr.creart.gamestack.common.i18n.Translator;
import fr.creart.gamestack.common.thread.ThreadsManager;

/**
 * Centralizes the common library for GameStack softwares.
 * You just have to create the logger on your own.
 *
 * @author Creart
 */
public final class Commons {

    private static boolean initialized;

    private static String softwareName;
    private static ThreadsManager threadsManager;

    private Commons()
    {

    }

    /**
     * Initializes commons
     *
     * @param soft Software name
     */
    public static void initialize(String soft)
    {
        if (initialized)
            return;

        softwareName = soft;

        threadsManager = new ThreadsManager(soft);
        Translator.initialize();

        initialized = true;
    }

    public static ThreadsManager getThreadsManager()
    {
        return threadsManager;
    }

    public static String getSoftwareName()
    {
        return softwareName;
    }

}
