/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.conf;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 * @author Creart
 */
public class PropertiesConfiguration extends Configuration {

    private Properties properties;

    public PropertiesConfiguration(File src)
    {
        super(src);
    }

    @Override
    public void set(String path, Object object)
    {
        properties.put(path, object);
    }

    @Override
    public void saveChanges()
    {
        try (FileWriter writer = new FileWriter(src)) {
            properties.store(writer, "");
        } catch (Exception e) {
            logger.error(String.format("Could not save changes to .properties file (%s)", src.getName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String path, T def)
    {
        Object ret = properties.get(path);
        if (ret == null)
            return def;
        try {
            return (T) ret;
        } catch (Exception e) {
            // ignore exception, could not cast.
            return def;
        }
    }

    @Override
    public void initialise()
    {
        try {
            properties = new Properties();
            properties.load(new FileReader(src));
        } catch (Exception e) {
            logger.error(String.format("Could not load configuration from file %s.", src.getName()), e);
        }
    }

}
