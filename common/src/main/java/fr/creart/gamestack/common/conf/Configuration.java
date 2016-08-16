/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.conf;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.misc.Initialisable;
import java.io.File;
import org.apache.log4j.Logger;

/**
 * Represents a configuration, from which you can obtain data by specifying its path, and where you can set/update data.
 * Everything is stored and read from files.
 *
 * @author Creart
 */
@SuppressWarnings("unchecked")
public abstract class Configuration implements Initialisable {

    protected final Logger logger = Logger.getLogger("Configuration");
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
     * Returns the value associated to the given path, <tt>def</tt> if failed
     *
     * @param path path to value
     * @param def  default value
     * @param <T>  type of the object
     * @return the value associated to the given path
     */
    public abstract <T> T get(String path, T def);

    /**
     * Returns the String associated to the given path, def if failed
     *
     * @param path path to value
     * @param def  default value
     * @return the String associated to the given path
     */
    public final String getString(String path, String def)
    {
        return get(path, def);
    }

    /**
     * Returns the integer associated to the given path, def if failed
     *
     * @param path path to value
     * @param def  default value
     * @return the integer associated to the given path
     */
    public final int getInteger(String path, int def)
    {
        return Integer.parseInt(getString(path, String.valueOf(def)));
    }

    /**
     * Returns the boolean associated to the given path, def if failed
     *
     * @param path path to value
     * @param def  default value
     * @return the boolean associated to the given path
     */
    public final boolean getBoolean(String path, boolean def)
    {
        return Boolean.parseBoolean(getString(path, String.valueOf(def)));
    }

    /**
     * Returns the double associated to the given path, def if failed
     *
     * @param path path to value
     * @param def  default value
     * @return the double associated to the given path
     */
    public final double getDouble(String path, double def)
    {
        return Double.parseDouble(getString(path, String.valueOf(def)));
    }

}
