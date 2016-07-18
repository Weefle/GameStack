package fr.creart.gamestack.common.conf;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import fr.creart.gamestack.common.lang.Validation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 * Yaml implementation for the {@link Configuration} class.
 *
 * @author Creart
 */
public class YamlConfiguration extends Configuration {

    private Map<String, Object> data;

    public YamlConfiguration(File src)
    {
        super(src);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize()
    {
        data = (Map<String, Object>) new Yaml().load(src.getAbsolutePath());
    }

    @Override
    public void saveChanges()
    {
        if (data == null || data.isEmpty())
            return;

        try {
            new Yaml().dump(data, new BufferedWriter(new FileWriter(src)));
        } catch (IOException e) {
            Throwables.propagate(e); // should not happen.
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Validation get(String path)
    {
        Preconditions.checkNotNull(Strings.emptyToNull(path), "path is null or empty");
        Object ret = data.get(path);
        if (ret == null)
            return new Validation.Failure<Exception, T>(new NullPointerException("path is null " + path));
        try {
            T t = (T) ret;
            return new Validation.Success<T, Exception>(t);
        } catch (Exception e) {
            return new Validation.Failure<Exception, T>(e);
        }
    }

    @Override
    public void set(String path, Object object)
    {
        Preconditions.checkNotNull(Strings.emptyToNull(path), "path is null or empty");
        data.put(path, object);
    }

}
