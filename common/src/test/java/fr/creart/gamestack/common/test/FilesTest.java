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
