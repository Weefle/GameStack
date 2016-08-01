package fr.creart.gamestack.common;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.broking.AbstractBrokerManager;
import fr.creart.gamestack.common.broking.BrokerManager;
import fr.creart.gamestack.common.i18n.Translator;
import fr.creart.gamestack.common.misc.ConnectionData;
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
    private static AbstractBrokerManager<?> broker;

    private Commons()
    {

    }

    /**
     * Initializes commons.
     * Initialize the message broker by calling {@link #connectMessageBroker(ConnectionData, AbstractBrokerManager)}
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

    /**
     * Returns the current software name
     *
     * @return the current software name
     */
    public static String getSoftwareName()
    {
        return softwareName;
    }

    /**
     * Returns the current broker manager
     *
     * @return the current broker manager
     */
    public static BrokerManager getMessageBroker()
    {
        return broker;
    }

    /**
     * Connects the broker manager and sets it to the common's current
     *
     * @param data   Connection data (host, port, credentials...)
     * @param broker Message broker's manager
     */
    public static void connectMessageBroker(ConnectionData data, AbstractBrokerManager<?> broker)
    {
        Preconditions.checkNotNull(data, "data can't be null");
        Preconditions.checkNotNull(broker, "broker can't be null");
        Preconditions.checkArgument(!broker.isConnectionEstablished(), "connection already established");

        broker.setConnectionData(data);
        broker.initialize();

        Commons.broker = broker;
    }

}
