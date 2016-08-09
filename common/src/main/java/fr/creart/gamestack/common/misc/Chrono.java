/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
    public long differenceAs(TimeUnit baseUnit, TimeUnit unit)
    {
        long duration = end - start;
        switch (unit) {
            case NANOSECONDS:
                return baseUnit.toNanos(duration);
            case MICROSECONDS:
                return baseUnit.toMicros(duration);
            case MILLISECONDS:
                return baseUnit.toMillis(duration);
            case SECONDS:
                return baseUnit.toSeconds(duration);
            case MINUTES:
                return baseUnit.toMinutes(duration);
            case HOURS:
                return baseUnit.toHours(duration);
            case DAYS:
                return baseUnit.toDays(duration);
            default:
                return baseUnit.toSeconds(duration);
        }
    }

    private long now(TimeUnit unit)
    {
        return unit == TimeUnit.MILLISECONDS ? System.currentTimeMillis() : unit.convert(System.nanoTime(), unit);
    }

}
