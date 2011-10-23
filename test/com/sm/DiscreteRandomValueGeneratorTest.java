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
    private DiscreteRandomValueGenerator generator1;
    private DiscreteRandomValueGenerator generator2;
    private DiscreteRandomValueGenerator generator3;

    @Before
    public void init() {
        dt.put(new IntDiscreteValue(2), new Probability(0.1));
        dt.put(new IntDiscreteValue(4), new Probability(0.2));
        dt.put(new IntDiscreteValue(3), new Probability(0.5));
        dt.put(new IntDiscreteValue(5), new Probability(0.1));
        dt.put(new IntDiscreteValue(1), new Probability(0.1));

        generator1 = DiscreteRandomValueGenerator.get(dt);
        generator2 = DiscreteRandomValueGenerator.get(dt);
        generator3 = DiscreteRandomValueGenerator.get(dt);

        DistributionTable dt2 = new DistributionTable();
        dt2.put(new IntDiscreteValue(7), new Probability(0.3));
        dt2.put(new IntDiscreteValue(9), new Probability(0.5));
        dt2.put(new IntDiscreteValue(10), new Probability(0.2));
        generator2 = DiscreteRandomValueGenerator.get(dt2);
    }

    @Test
    public void test_Get_Next() throws Exception {
        Experiment experiment = new Experiment(generator1);
        experiment.run(100000);

//        System.out.println("experiments = " + Arrays.toString(experiment.getMeasurements().toArray()));

        DistributionFunction df = ModellingDF.get().createSingle(experiment);
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

        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator1);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

        DistributionFunction df = ModellingDF.get().createAndParallelism(ce);

        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("Compatible events distribution table:\n" + dt_);

        double error = 0.01;
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(0).getValue() - 0.01) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(1).getValue() - 0.03) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(2).getValue() - 0.45) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(3).getValue() - 0.32) < error, true);
        Assert.assertEquals(Math.abs(dt_.getProbabilityInRow(4).getValue() - 0.19) < error, true);

        pAll.finishPrint();
    }

    @Test
    public void test_compatible_distribution_functions_analytical() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        new CompatibleExperiments(experiment1, experiment2).run(100000);

        // analytical
        DistributionFunction df1 = ModellingDF.get().createSingle(experiment1);
        DistributionFunction df2 = ModellingDF.get().createSingle(experiment2);
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(df1, df2);

        DistributionFunction dfAnalytical = AnalyticalDF.get().createAND(cdf);


        // modelling
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        DistributionFunction dfModelling = ModellingDF.get().createAndParallelism(ce);

        double error = 0.005;
        assertEquals(dfModelling, dfAnalytical, error);
    }

    @Test
    public void test_modelling_OR_parallelism() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator1);
//        Experiment experiment3 = new Experiment(generator3);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);
        System.out.println("OR distribution table (modelling):\n" + orParallelismDF.getDistributionTable());

        double error = 0.01;

        assertEquals(0.1909807731, orParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.1688273568, orParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.550618158, orParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
        assertEquals(0.0795988158, orParallelismDF.getDistributionTable().getProbabilityInRow(3).getValue(), error);
        assertEquals(0.0099748962, orParallelismDF.getDistributionTable().getProbabilityInRow(4).getValue(), error);
    }

    @Test
    public void test_analytical_OR_parallelism() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
//        Experiment experiment3 = new Experiment(generator3);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);
        System.out.println("OR distribution table (modelling):\n" + orParallelismDF.getDistributionTable());

        DistributionFunction df1 = ModellingDF.get().createSingle(experiment1);
        DistributionFunction df2 = ModellingDF.get().createSingle(experiment2);

        DistributionFunction modellingDF = ModellingDF.get().createOrParallelism(ce);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(df1, df2);
        DistributionFunction analyticalDF = AnalyticalDF.get().createOR(cdf);

        System.out.println("OR distribution table (analytical):\n" + analyticalDF.getDistributionTable());

        double error = 0.005;

        assertEquals(modellingDF, analyticalDF, error);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        double error = 0.001;

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, error);
    }

    @Test
    public void test_modelling_MN_parallelism_3_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 3);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        double error = 0.001;

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, error);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_2() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        double error = 0.001;

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, error);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(100000);

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
//        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        double error = 0.001;

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, error);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator1);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(100000);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());


        double error = 0.01;

        assertEquals(0.0284, mnParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.07698, mnParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.67728, mnParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
        assertEquals(0.18962, mnParallelismDF.getDistributionTable().getProbabilityInRow(3).getValue(), error);
        assertEquals(0.02772, mnParallelismDF.getDistributionTable().getProbabilityInRow(4).getValue(), error);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(100000);
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMNParallelism(ce, M);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3));


        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        double error = 0.01;

        assertEquals(mnModellingDF, mnParallelismDF, error);
    }

    @Test
    public void test_modelling_sequence_invoke() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

        DistributionFunction sequenceDF = ModellingDF.get().createSequenceProcessing(ce);
        System.out.println("Sequence processing distribution table (modelling):\n" + sequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(ModellingDF.get().createSingle(experiment1)).getValue();
        double expVal2 = new ExpectedValue(ModellingDF.get().createSingle(experiment2)).getValue();
        double expVal12 = new ExpectedValue(sequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + new ExpectedValue(ModellingDF.get().createSingle(experiment1)));
        System.out.println("Expected value of the experiment 2: " + new ExpectedValue(ModellingDF.get().createSingle(experiment2)));
        System.out.println("Expected value of the sequence of2: " + new ExpectedValue(sequenceDF));

        double error = 0.05;
//        assertEquals(expVal1, expVal2, error);
        assertEquals(expVal1 + expVal2, expVal12, error);
    }

    @Test
    public void test_analytical_sequence_invoke() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(100000);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2));

        DistributionFunction analyticalSequenceDF = AnalyticalDF.get().createSequenceProcessing(cdf);
        DistributionFunction modellingSequenceDF = ModellingDF.get().createSequenceProcessing(ce);

        System.out.println("Sequence processing distribution table (analytical):\n" + analyticalSequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(ModellingDF.get().createSingle(experiment1)).getValue();
        double expVal2 = new ExpectedValue(ModellingDF.get().createSingle(experiment2)).getValue();
        double expVal12 = new ExpectedValue(analyticalSequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + new ExpectedValue(ModellingDF.get().createSingle(experiment1)));
        System.out.println("Expected value of the experiment 2: " + new ExpectedValue(ModellingDF.get().createSingle(experiment2)));
        System.out.println("Expected value of the sequence of2: " + new ExpectedValue(analyticalSequenceDF));

        double error = 0.01;
        assertEquals(expVal1, expVal2, error);
        assertEquals(expVal1 + expVal2, expVal12, error);

        assertEquals(modellingSequenceDF, analyticalSequenceDF, error);
    }

    private static void assertEquals(double a, double b, double error) {
        Assert.assertEquals(a + " and " + b + " are not equal with error " + error, true, Math.abs(a - b) < error);
    }

    private static boolean assertEquals(DistributionFunction df1, DistributionFunction df2, double error) {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(df1, df2);
//        assert df1.getDistributionTable().size() == df2.getDistributionTable().size() : "compared DFs should have the same size";
        for (DiscreteValue dv : cdf.getDiscreteValueSet()) {
            assertEquals(df1.eval(dv), df2.eval(dv), error);
        }
        return true;
    }

}
