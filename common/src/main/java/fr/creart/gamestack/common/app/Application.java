/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.app;

import fr.creart.gamestack.common.Commons;
import fr.creart.gamestack.common.lang.Decimals;
import fr.creart.gamestack.common.lang.UnsafeRunnable;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.Chrono;
import java.util.concurrent.TimeUnit;

/**
 * Represents an application
 *
 * @author Creart
 */
public abstract class Application implements UnsafeRunnable {

    /**
     * Runs the usual initialisation steps and the program itself.
     *
     * @param soft   software's name
     * @param logger logger's name
     */
    protected final void startup(String soft, String logger)
    {
        CommonLogger.createLogger(logger);
        CommonLogger.info("Starting up " + soft + "...");
        Chrono chrono = new Chrono();
        chrono.markStart(TimeUnit.MILLISECONDS);

        try {
            load();
        } catch (Exception e) {
            CommonLogger.fatal("Encountered an unhandled exception during the startup.", e);
            CommonLogger.fatal("Exiting...");
            System.exit(1);
        }

        chrono.markEnd(TimeUnit.MILLISECONDS);
        CommonLogger.info("Done (~" + Decimals.firstDecimals((double) chrono.differenceAs(TimeUnit.MILLISECONDS, TimeUnit.MILLISECONDS) / 1000, 1) + "s.)");

        byte code = 0; // lol bytecode (it's a joke, of course...)

        try {
            run();
        } catch (Exception e) {
            CommonLogger.fatal("An unhandled exception has been encountered during the execution of the program.", e);
            code = 1;
        } finally {
            Commons.getInstance().close();
        }

        CommonLogger.info("Thank you for using GameStack. Good-bye!");
        CommonLogger.close();
        System.exit(code);
    }

    protected abstract void load() throws Exception;

}
