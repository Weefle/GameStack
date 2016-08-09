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

package fr.creart.gamestack.common.conf;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.misc.Initialisable;
import java.io.File;

/**
 * Represents a configuration, from which you can obtain data by specifying its path, and where you can set/update data.
 * Everything is stored and read from files.
 *
 * @author Creart
 */
@SuppressWarnings("unchecked")
public abstract class Configuration implements Initialisable {

    protected final File src;

    /**
     * Default constructor.
     *
     * @param src The source file.
     */
    public Configuration(File src)
    {
        Preconditions.checkNotNull(src, "src can't be null");
        Preconditions.checkArgument(src.isFile() && src.exists(), "src has to be a file and must exist");
        this.src = src;
    }

    /**
     * Updates the value associated to the path
     *
     * @param path   Path
     * @param object The value of the path
     */
    public abstract void set(String path, Object object);

    /**
     * Saves the changes in the file.
     */
    public abstract void saveChanges();

    /**
     * Updates the value associated to the path and saves the changes
     *
     * @param path   Path
     * @param object The value of the path
     */
    public final void setAndSave(String path, Object object)
    {
        set(path, object);
        saveChanges();
    }

    /**
     * Returns a validation. Contains the value if no exception has been raised.
     * If it fails, it returns a failure with the reason of the exception.
     *
     * @param path Path
     * @param <T>  T type of the checked object
     * @return a validation, with the value, or an exception.
     */
    public abstract <T> T get(String path, T def);

    public final int getInteger(String path, int def)
    {
        return get(path, def);
    }

    public final String getString(String path, String def)
    {
        return get(path, def);
    }

    public final boolean getBoolean(String path, boolean def)
    {
        return get(path, def);
    }

    public final double getDouble(String path, double def)
    {
        return get(path, def);
    }

}
