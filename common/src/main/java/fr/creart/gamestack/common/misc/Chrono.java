package fr.creart.gamestack.common.misc;

import java.util.concurrent.TimeUnit;

/**
 * A "chronometer" which allows you to calculate the time passed between the two moments.
 *
 * @author Creart
 */
public final class Chrono {

    private long start;
    private long end;

    /**
     * Marks the position of the start.
     *
     * @param unit time unit
     */
    public void markStart(TimeUnit unit)
    {
        start = now(unit);
    }

    /**
     * Marks the position of the end.
     *
     * @param unit time unit
     */
    public void markEnd(TimeUnit unit)
    {
        end = now(unit);
    }

    /**
     * Returns the difference between the end and the start positions.
     *
     * @return the difference between the end and the start positions.
     */
    public long difference()
    {
        return end - start;
    }

    private long now(TimeUnit unit)
    {
        return unit == TimeUnit.MILLISECONDS ? System.currentTimeMillis() : unit.convert(System.nanoTime(), unit);
    }

}
