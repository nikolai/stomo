package com.sm.util;

import com.sm.CompatibleDistributionFunctions;
import com.sm.DiscreteValue;
import com.sm.DistributionFunction;
import com.sm.DistributionTable;
import junit.framework.Assert;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 20:23
 */
public class AssertUtil {
    public static final double ANALYTICAL_ERROR = 0.005;

    public static void assertEquals(double expected, double real, double error) {
        Assert.assertEquals(expected + " and " + real + " are not equal with error " + error, true, Math.abs(expected - real) < error);
    }

    public static boolean assertEquals(DistributionFunction expected, DistributionFunction real, double error) {
        CompatibleDistributionFunctions<Integer> cdf = new CompatibleDistributionFunctions<Integer>(expected, real);
//        assert df1.getDistributionTable().size() == df2.getDistributionTable().size() : "compared DFs should have the same size";
        for (DiscreteValue<Integer> dv : cdf.getDiscreteValueSet()) {
            assertEquals(expected.eval(dv), real.eval(dv), error);
        }
        return true;
    }

    public static boolean assertEquals(DistributionTable expected, DistributionTable real, double error) {
        return assertEquals(DistributionFunction.createByTable(expected), DistributionFunction.createByTable(real), error);
    }
}
