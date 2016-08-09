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

package fr.creart.gamestack.common.command;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Represents a command which can be executed from the console.
 *
 * @author Creart
 */
public abstract class Command {

    @Getter(AccessLevel.PACKAGE)
    private final String label;
    @Getter(AccessLevel.PACKAGE)
    private final String[] aliases;
    @Getter(AccessLevel.PACKAGE)
    private final String help;

    /**
     * Default constructor
     *
     * @param label     Command's label (its name)
     * @param aliases   Command's aliases (other names)
     */
    public Command(String label, String help, String... aliases)
    {
        this.label = label;
        this.help = help;
        this.aliases = aliases;
    }

    /**
     * Default constructor "helper"
     *
     * @param label     Command's label (its name)
     * @param aliases   Command's aliases (other names)
     */
    public Command(String label, String help, Collection<String> aliases)
    {
        this(label, help, (String[]) aliases.toArray());
    }

    /**
     * Called on the command execution.
     *
     * @param args  Provided arguments
     */
    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public final boolean equals(Object obj)
    {
        return obj instanceof Command && label.equals(((Command) obj).label);
    }

}
