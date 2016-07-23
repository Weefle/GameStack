package fr.creart.gamestack.server;

import fr.creart.gamestack.common.lang.Decimals;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Chrono;

/**
 * @author Creart
 */
public class Main {

    public static void main(String[] args)
    {
        CommonLogger.createLogger("Server");
        CommonLogger.info("Starting up GameStack server...");
        Chrono chrono = new Chrono();

        chrono.markStart(null);

        // start up

        chrono.markEnd(null);

        CommonLogger.info("Done (~" + Decimals.firstDecimals(((double) chrono.difference()) / 1000, 1) + "s).");
    }

}
