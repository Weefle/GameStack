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

import fr.creart.gamestack.common.lang.Validation;
import fr.creart.gamestack.common.lang.Validation.Failure;
import fr.creart.gamestack.common.lang.Validation.Success;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Creart
 */
public class ValidationTest {

    static Validation<Exception, Integer> toInteger(String string)
    {
        try {
            return new Success<>(Integer.valueOf(string));
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

    @Test
    public void testValidation()
    {
        Assert.assertTrue(!toInteger("aI350").isSuccess());
        Assert.assertTrue(toInteger("123").isSuccess());
    }

}
