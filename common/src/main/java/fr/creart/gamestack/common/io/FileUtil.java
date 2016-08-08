package fr.creart.gamestack.common.io;

import com.google.common.base.Preconditions;
import fr.creart.gamestack.common.lang.MoreArrays;
import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.lang.Validation.Failure;
import fr.creart.gamestack.common.lang.Validation.Success;
import fr.creart.gamestack.common.log.CommonLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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

    /**
     * Returns file's creation date or current time if failed
     *
     * @param file File
     * @return file's creation date
     */
    public static Instant getFileCreationDate(File file)
    {
        Preconditions.checkNotNull(file, "file can't be null");
        Preconditions.checkArgument(file.exists(), "file has to exist");

        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            return attr.creationTime().toInstant();
        } catch (Exception e) {
            CommonLogger.error("Could not get file creation date (file=" + file.toString() + ").", e);
            return Instant.now();
        }
    }

    /**
     * Adds files to a ZIP.
     * Returns <code>true</code> if the file has been successfully created
     *
     * @param dest Destination zip file
     * @param src  Sources files
     * @return <code>true</code> if the file has been successfully created
     */
    public static boolean addToZip(File dest, File... src)
    {
        Preconditions.checkNotNull(src, "src can't be null");
        Preconditions.checkNotNull(dest, "dest can't be null");
        Preconditions.checkArgument(src.length > 0, "no src provided");
        for (File file : src)
            Preconditions.checkArgument(file != null && file.exists() && file.isFile(), "src null or does not exist");
        Preconditions.checkArgument(dest.exists(), "dest has to exist");

        try {
            byte[] buf = new byte[1024];

            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dest));

            for (File file : src) {
                InputStream in = new FileInputStream(file);
                int read;
                zout.putNextEntry(new ZipEntry(file.getName()));
                while ((read = in.read(buf)) > 0)
                    zout.write(buf, 0, read);
                zout.closeEntry();
                in.close();
            }

            zout.flush();
            zout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
