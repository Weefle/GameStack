/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.log;

/**
 * @author Creart
 */
class CustomLayout {

    /*private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("'['HH':'mm']' ");

    private boolean file;

    /**
     * @param file <tt>true</tt> if the layout is for file appending (does not print debug)
    CustomLayout(boolean file)
    {
        this.file = file;
    }

    @Override
    public String format(LoggingEvent event)
    {
        if (event == null || (file && event.getLevel() == Level.DEBUG))
            return null;

        StringBuilder builder = new StringBuilder(); // StringBuilders are more efficient than simple String concatenations
        builder.append(currentTime());
        builder.append('[').append(event.getLogger().getName()).append("] ");
        builder.append(event.getLevel().toString()).append(": ");
        builder.append(event.getMessage());

        if (event.getThrowableInformation() != null)
            builder.append(" Exception:\n").append(ExceptionUtils.getStackTrace(event.getThrowableInformation().getThrowable()));

        builder.append("\n");

        return builder.toString();
    }

    @Override
    public boolean ignoresThrowable()
    {
        return false;
    }

    @Override
    public void activateOptions()
    {
        // nothing to do here.
    }

    private static String currentTime()
    {
        return LocalDateTime.now().format(DATE_FORMAT);
    }*/

}
