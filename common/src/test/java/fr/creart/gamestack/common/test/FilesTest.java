package fr.creart.gamestack.common.test;

import fr.creart.gamestack.common.io.FileUtil;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Creart
 */
public class FilesTest {

    @Test
    public void cleanNameTest()
    {

        File file = new File("pom.xml");
        Assert.assertEquals("pom", FileUtil.getFileCleanName(file));

    }

    @Test
    public void fileExtensionTest()
    {

        File file = new File("pom.xml");
        Assert.assertEquals("xml", FileUtil.getFileExtension(file));

    }

}
