package fr.creart.gamestack.common.thread;

/**
 * Some threading utils
 *
 * @author Creart
 */
public class ThreadsUtil {

    private ThreadsUtil()
    {

    }

    /**
     * Sleeps the thread, if an {@link InterruptedException} is thrown it interrupts the thread.
     *
     * @param time Time to sleep
     */
    public static void sleep(long time)
    {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}
