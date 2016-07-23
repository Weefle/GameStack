package fr.creart.gamestack.common.test;

import fr.creart.gamestack.common.lang.Decimals;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Creart
 */
public class DecimalsTest {

    @Test
    public void firstDecimals()
    {
        Assert.assertEquals("1.7", Decimals.firstDecimals(1.6565655, 1));
        Assert.assertEquals("1.56", Decimals.firstDecimals(1.555555, 2));
    }

}
