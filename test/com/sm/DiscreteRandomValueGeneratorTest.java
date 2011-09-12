package com.sm;

import com.sm.profiling.SimpleProfiler;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 20:13
 */
public class DiscreteRandomValueGeneratorTest {
    private DistributionTable dt = new DistributionTable();

    @Before
    public void init() {
        dt.put(new IntDiscreteValue(4), new Probability(0.2));
        dt.put(new IntDiscreteValue(3), new Probability(0.5));
        dt.put(new IntDiscreteValue(2), new Probability(0.1));
        dt.put(new IntDiscreteValue(5), new Probability(0.1));
        dt.put(new IntDiscreteValue(1), new Probability(0.1));
    }

    @Test
    public void test_Get_Next() throws Exception {
        DiscreteRandomValueGenerator generator = DiscreteRandomValueGenerator.get(dt);

        Experiment experiment = new Experiment();
        for (int i = 0; i < 100000; i++) {
            experiment.addMeasurement(generator.getNext());
        }

//        System.out.println("experiments = " + Arrays.toString(experiment.getMeasurements().toArray()));

        DistributionFunction df = new DistributionFunction(experiment);
        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("distribTable:\n" + dt_.toString());


        Assert.assertEquals(dt_.size(), 5);

        Assert.assertTrue(dt_.getDiscreteValueInRow(0).getValue().equals(1));
        Assert.assertTrue(dt_.getDiscreteValueInRow(1).getValue().equals(2));
        Assert.assertTrue(dt_.getDiscreteValueInRow(2).getValue().equals(3));
        Assert.assertTrue(dt_.getDiscreteValueInRow(3).getValue().equals(4));
        Assert.assertTrue(dt_.getDiscreteValueInRow(4).getValue().equals(5));

        double error = 0.01;

        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(0).getValue() - 0.1) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(1).getValue() - 0.1) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(2).getValue() - 0.5) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(3).getValue() - 0.2) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(4).getValue() - 0.1) < error, true);
    }

    @Test
    public void test_compatible_events() {

        SimpleProfiler pAll = SimpleProfiler.start("all");

        DiscreteRandomValueGenerator generator1 = DiscreteRandomValueGenerator.get(dt);
        DiscreteRandomValueGenerator generator2 = DiscreteRandomValueGenerator.get(dt);

        Experiment experiment1 = new Experiment();
        Experiment experiment2 = new Experiment();
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        for (int i = 0; i < 1000000; i++) {
            experiment1.addMeasurement(generator1.getNext());
            experiment2.addMeasurement(generator2.getNext());
        }

        DistributionFunction df = new DistributionFunction(ce);

        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("Compatible events distribution table:\n" + dt_);

        double error = 0.001;
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(0).getValue() - 0.01) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(1).getValue() - 0.03) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(2).getValue() - 0.45) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(3).getValue() - 0.32) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(4).getValue() - 0.19) < error, true);

        pAll.finishPrint();
    }
}
