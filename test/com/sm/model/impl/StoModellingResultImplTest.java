package com.sm.model.impl;

import com.sm.DistributionFunction;
import com.sm.DistributionTable;
import org.junit.Before;
import org.junit.Test;

import static com.sm.util.AssertUtil.ANALYTICAL_ERROR;
import static com.sm.util.AssertUtil.assertEquals;

/**
 * User: mikola
 * Date: 26.02.14
 * Time: 1:03
 */
public class StoModellingResultImplTest {
    StoModellingResultImpl res;
    @Before
    public void before() {
        DistributionFunction df = DistributionFunction.createByTable(
                new DistributionTable<Integer>(new int[] {1, 2 , 3}, new double[] {0.94, 0.03, 0.03}));
        res = new StoModellingResultImpl(df);
    }

    @Test
    public void testEvalRisk() throws Exception {
        assertEquals(0.03, res.evalRisk(2).getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void testGetVariance() throws Exception {
        assertEquals(4.46, res.getVariance().getValue(), ANALYTICAL_ERROR);
    }

    @Test
    public void testGetExpectedValue() throws Exception {
        assertEquals(1.09, res.getExpectedValue().getValue(), ANALYTICAL_ERROR);
    }


}
