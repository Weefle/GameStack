package fr.creart.gamestack.common.io;

import fr.creart.gamestack.common.lang.MoreArrays;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.lang.Validation.Failure;
import fr.creart.gamestack.common.lang.Validation.Success;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Arrays;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * File utils
 *
 * @author Creart
 */
public final class FileUtil {

    private FileUtil()
    {

    }

    /**
     * Returns the extension of the file
     *
     * @param file File
     * @return the extension of the file
     */
    public static String getFileExtension(File file)
    {
        String[] bits = file.getName().split("\\.");
        return MoreArrays.lastObjectOf(bits);
    }

    /**
     * Returns the clean name of the file (without the extension or the directory location)
     *
     * @param file File
     * @return the clean name of the file
     */
    public static String getFileCleanName(File file)
    {
        String[] bits = file.getName().split("/");
        String name = MoreArrays.lastObjectOf(bits);
        String[] points = name.split("\\.");
        return MoreArrays.concat(Arrays.copyOf(points, points.length - 1));
    }

    /**
     * Copies the file from java resources to the output path.
     * Returns the created file.
     *
     * @param file       File name to copy
     * @param outputPath Output
     * @param replace    If {@code true}, replaces the existing file.
     * @return the created file.
     */
    public static Validation<Exception, File> saveResource(String file, String outputPath, boolean replace)
    {
        ClassLoader loader = FileUtil.class.getClassLoader();
        URL url = loader.getResource(file);

        if (url == null)
            return new Failure<>(new FileNotFoundException(file));

        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            File outFile = new File(outputPath);

            if (outFile.exists())
                if (!replace)
                    return new Success<>(outFile);
                else if (!outFile.delete())
                    return new Failure<>(new InvalidStateException("Could not override existing file."));

            Files.copy(connection.getInputStream(), outFile.toPath());
            return new Success<>(outFile);
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

}
