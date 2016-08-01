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
     * Because it is very redundant to always try and catch to sleep a thread,
     * I created this function. The {@link InterruptedException} isn't something
     * you do a lot about.
     *
     * @param time Time to sleep
     */
    public static void sleep(long time)
    {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            // useless
        }
    }

}
