package fr.creart.gamestack.common.io;

import fr.creart.gamestack.common.lang.MoreArrays;
import java.io.File;
import java.util.Arrays;

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

}
