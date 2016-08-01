package fr.creart.gamestack.common.connection.database.sql;

/**
 * MariaDB implementation
 *
 * @author Creart
 */
public class MariaDB extends SQLDatabase {

    public MariaDB(int threads)
    {
        super(threads);
        databaseSystemName = "mariadb";
        driver = "org.mariadb.jdbc.Driver";
    }

    @Override
    protected String getServiceName()
    {
        return "MariaDB";
    }

}
