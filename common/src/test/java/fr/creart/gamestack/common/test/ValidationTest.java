package fr.creart.gamestack.common.test;

import fr.creart.gamestack.common.Validation;
import fr.creart.gamestack.common.Validation.Failure;
import fr.creart.gamestack.common.Validation.Success;
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
