/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.broking.AbstractBrokerManager;
import fr.creart.gamestack.common.broking.BrokerManager;
import fr.creart.gamestack.common.connection.ConnectionData;
import fr.creart.gamestack.common.connection.database.AbstractDatabase;
import fr.creart.gamestack.common.connection.database.AbstractRequest;
import fr.creart.gamestack.common.connection.database.Database;
import fr.creart.gamestack.common.connection.database.DatabaseConnectionData;
import fr.creart.gamestack.common.connection.database.sql.SQLRequest;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.metric.BrokerMetricOutput;
import fr.creart.gamestack.common.metric.MetricsManager;
import fr.creart.gamestack.common.text.Translator;
import fr.creart.gamestack.common.thread.ThreadsManager;

/**
 * Centralizes the common library for GameStack software.
 * You just have to create the logger on your own.
 *
 * @author Creart
 */
public final class Commons {

    private static Commons instance;

    private boolean initialized;

    private String softwareName;
    private ThreadsManager threadsManager;
    private MetricsManager metricsManager;
    private AbstractBrokerManager<?, ?> broker;
    /**
     * Note: Only SQL support for the moment
     */
    private Database<SQLRequest> database;
    private Translator translator;

    private Commons()
    {

    }

    /**
     * Initializes more basic stuff of commons
     *
     * @param soft Soft's name
     */
    public void initialize(String soft)
    {
        this.softwareName = soft;
        threadsManager = new ThreadsManager(soft);
        translator = new Translator("messages.properties");
        translator.initialize();
    }

    /**
     * Connects commons to the network.
     *
     * @param broker             the message broker implementation
     * @param brokerConnection   the connection data to the broker
     * @param database           the database implementation
     * @param databaseConnection the data used to connect to the database
     * @param metricsThreads     number of threads allocated to the metrics
     */
    public <T, V extends ConnectionData, S extends DatabaseConnectionData>
    void connect(V brokerConnection, AbstractBrokerManager<T, V> broker,
                 S databaseConnection, AbstractDatabase<?, SQLRequest, S> database, byte metricsThreads)
    {
        if (initialized)
            return;

        connectMessageBroker(brokerConnection, broker);
        connectDatabase(databaseConnection, database);
        initializeMetricsManager(metricsThreads);

        initialized = true;
    }

    /**
     * Returns the current threads manager
     *
     * @return the current threads manager
     */
    public ThreadsManager getThreadsManager()
    {
        return threadsManager;
    }

    /**
     * Returns the current software name
     *
     * @return the current software name
     */
    public String getSoftwareName()
    {
        return softwareName;
    }

    /**
     * Returns the current database
     *
     * @return the current database
     */
    public Database<SQLRequest> getDatabase()
    {
        return database;
    }

    /**
     * Returns the current broker manager
     *
     * @return the current broker manager
     */
    public BrokerManager getMessageBroker()
    {
        return broker;
    }

    /**
     * Returns the current metrics manager
     *
     * @return the current metrics manager
     */
    public MetricsManager getMetricsManager()
    {
        return metricsManager;
    }

    /**
     * Returns the current translator
     *
     * @return the current translator
     */
    public Translator getTranslator()
    {
        return translator;
    }

    /**
     * Connects the broker manager and sets it to the common's current
     *
     * @param data   Connection data (host, port, credentials...)
     * @param broker Message broker's manager
     */
    private <T, D extends ConnectionData> void connectMessageBroker(D data, AbstractBrokerManager<T, D> broker)
    {
        Preconditions.checkNotNull(data, "data can't be null");
        Preconditions.checkNotNull(broker, "broker can't be null");
        Preconditions.checkArgument(!broker.isConnectionEstablished(), "connection already established");

        broker.initialize(data);
        this.broker = broker;
    }

    /**
     * Connects the database and sets it to the common's current
     *
     * @param data     Connection data
     * @param database Database
     */
    private <DATA extends DatabaseConnectionData, T, Q extends AbstractRequest<?>> void connectDatabase(DATA data, AbstractDatabase<T, SQLRequest, DATA> database)
    {
        Preconditions.checkNotNull(data, "connection data can't be null");
        Preconditions.checkNotNull(database, "database can't be null");
        Preconditions.checkArgument(!database.isConnectionEstablished(), "connection already established");

        database.initialize(data);
        this.database = database;
    }

    /**
     * Initializes the metrics manager
     *
     * @param threads number of threads
     */
    private void initializeMetricsManager(byte threads)
    {
        metricsManager = new MetricsManager(new BrokerMetricOutput(broker), threads);
    }

    /**
     * Closes the commons.
     * Before that, make sure that you finished your processes which may use commons.
     */
    public void close()
    {
        if (!initialized)
            return;

        try {
            threadsManager.close();
        } catch (Exception e) {
            CommonLogger.error("Could not close the threads manager.", e);
        }

        if (broker != null)
            broker.close();

        initialized = false; // in order to avoid a second closure of the class, if the function is accidentally called twice.
    }

    /**
     * Returns the single instance of the Commons class
     *
     * @return the single instance of the Commons class
     */
    public static Commons getInstance()
    {
        if (instance == null)
            instance = new Commons();
        return instance;
    }

}
