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
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                    return new Failure<>(new IllegalStateException("Could not override existing file."));

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
     * @param destination Destination zip file
     * @param sources     Sources files
     * @return <code>true</code> if the file has been successfully created
     */
    public static boolean addToZip(File destination, File... sources)
    {
        Preconditions.checkNotNull(sources, "src can't be null");
        Preconditions.checkNotNull(destination, "destination can't be null");
        Preconditions.checkArgument(sources.length > 0, "no src provided");
        for (File file : sources)
            Preconditions.checkArgument(file != null && file.exists() && file.isFile(), "src null or does not exist");
        Preconditions.checkArgument(destination.exists(), "destination has to exist");

        try {
            byte[] buf = new byte[1024];

            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(destination));

            for (File file : sources) {
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
            CommonLogger.error("Could not compress file to zip (dest=" + destination + ",sources=" + Arrays.toString(sources) + ").");
            return false;
        }
    }

    /**
     * Copies the given folder's contents to the destination folder
     *
     * @param source      Source folder
     * @param destination Destination folder
     */
    public static void copyFolder(File source, File destination)
    {
        Preconditions.checkNotNull(source, "source can't be null");
        Preconditions.checkNotNull(destination, "destination can't be null");

        if (source.isFile())
            copyFile(source, destination);
        else {
            if (!destination.exists())
                destination.mkdirs();
            doCopyFolder(source, destination);
        }
    }

    private static void doCopyFolder(File source, File destination)
    {
        for (File file : source.listFiles())
            if (file != null && file.isFile())
                copyFile(file, new File(destination.getAbsolutePath() + file.getName()));
            else
                doCopyFolder(source, destination);
    }

    /**
     * Copies the source file to the destination.
     *
     * @param source      Source file
     * @param destination Destination file
     */
    public static void copyFile(File source, File destination)
    {
        Preconditions.checkNotNull(source, "source can't be null");
        Preconditions.checkNotNull(destination, "destination null");
        Preconditions.checkArgument(!source.exists(), "source does not exist");

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            if (!destination.exists())
                destination.createNewFile();
            out = new FileOutputStream(destination);
            ReadableByteChannel inChannel = Channels.newChannel(in);
            WritableByteChannel outChannel = Channels.newChannel(out);
            fastChannelCopy(inChannel, outChannel);
        } catch (Exception e) {
            CommonLogger.error(String.format("Could not copy files (source=%s,destination=%s).", source.getAbsolutePath(), destination.getAbsolutePath()), e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                CommonLogger.error("Could not close input or output stream.", e);
            }
        }
    }

    /**
     * Copies the source channel to the destination channel.
     *
     * @param src  Source byte channel
     * @param dest Destination byte channel
     * @author Thomas Wabner (https://thomaswabner.wordpress.com/2007/10/09/fast-stream-copy-using-javanio-channels/)
     */
    private static void fastChannelCopy(ReadableByteChannel src, WritableByteChannel dest)
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        try {
            while (src.read(buffer) != -1) {
                buffer.flip();
                dest.write(buffer);
                buffer.compact();
            }
            buffer.flip();
            while (buffer.hasRemaining())
                dest.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
