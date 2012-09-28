package com.sm.util;

import com.sm.CompatibleDistributionFunctions;
import com.sm.DiscreteValue;
import com.sm.DistributionFunction;
import junit.framework.Assert;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 20:23
 */
public class AssertUtil {
    public static final double ANALYTICAL_ERROR = 0.005;

    public static void assertEquals(double a, double b, double error) {
        Assert.assertEquals(a + " and " + b + " are not equal with error " + error, true, Math.abs(a - b) < error);
    }

    public static boolean assertEquals(DistributionFunction df1, DistributionFunction df2, double error) {
        CompatibleDistributionFunctions<Integer> cdf = new CompatibleDistributionFunctions<Integer>(df1, df2);
//        assert df1.getDistributionTable().size() == df2.getDistributionTable().size() : "compared DFs should have the same size";
        for (DiscreteValue<Integer> dv : cdf.getDiscreteValueSet()) {
            assertEquals(df1.eval(dv), df2.eval(dv), error);
        }
        return true;
    }
}
