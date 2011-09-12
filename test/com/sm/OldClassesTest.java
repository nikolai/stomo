package com.sm;

import com.test.DiscreteRandomValue;
import com.test.Distribution;
import com.test.ExpectedValue;
import com.test.Variance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: smirnov-n
 * Date: 12.09.11
 * Time: 17:19
 */
public class OldClassesTest {
    @Test
    public void test_old_main() {
        int experimentCount = 10000;
        Map<DiscreteRandomValue,int[]> allValues = new HashMap();

        DiscreteRandomValue rval = new DiscreteRandomValue(
            new int[] {1, 2, 3, 4, 5},
            new double[] {0.1, 0.1, 0.5, 0.2, 0.1}
        );
        DiscreteRandomValue rval2 = new DiscreteRandomValue(rval);
        allValues.put(rval, new int[experimentCount]);
        allValues.put(rval2, new int[experimentCount]);

        for (int i = 0; i < experimentCount; i++) {
            for (DiscreteRandomValue aVal : allValues.keySet()) {
                allValues.get(aVal)[i] = aVal.getNext();
            }
        }

        for (DiscreteRandomValue aVal : allValues.keySet()) {
            Distribution distr1 = new Distribution(allValues.get(aVal));
            System.out.println("Distribution:\t" + distr1);
            System.out.println("Expected value:\t" + new ExpectedValue(distr1));
            System.out.println("Variance:\t\t" + new Variance(distr1));
        }
        Distribution distr2 = new Distribution(allValues.get(rval), allValues.get(rval2));
        System.out.println("Distribution of 2:\t" + distr2);
        System.out.println("Expected value:\t" + new ExpectedValue(distr2));
        System.out.println("Variance:\t\t" + new Variance(distr2));
    }
}
