/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.misc;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Represents an object, a game, an instance of anything which needs files to work properly.
 * These latter are given by the {@link #getRequiredFiles()} function.
 * <p>
 * What makes this class interesting is that:
 * <ul>
 * <li>files are represented by Strings, so you can actually put URLs or use them as you like;</li>
 * <li>a FileDependingInstance can have a parent, so you can actually create a "hierarchy".</li>
 * </ul>
 *
 * @author Creart
 */
public abstract class FileDependingInstance {

    private FileDependingInstance parent;
    protected String[] requiredFiles;

    /**
     * @param requiredFiles the required files
     */
    public FileDependingInstance(String... requiredFiles)
    {
        this(null, requiredFiles);
    }

    /**
     * @param parent        the parent instance
     * @param requiredFiles the required files
     */
    public FileDependingInstance(FileDependingInstance parent, String... requiredFiles)
    {
        setParent(parent);
        this.requiredFiles = requiredFiles;
    }

    /**
     * Returns the required files of this instance, added to the required files of the parent instances.
     * The parent files come first.
     *
     * @return the required files of this instance, added to the required files of the parent instances
     */
    public String[] getRequiredFiles()
    {
        if (parent == null || parent.getRequiredFiles().length == 0)
            return requiredFiles;
        return ArrayUtils.addAll(parent.getRequiredFiles(), requiredFiles);
    }

    protected void setParent(FileDependingInstance parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof FileDependingInstance && Arrays.equals(requiredFiles, ((FileDependingInstance) obj).requiredFiles);
    }

}
