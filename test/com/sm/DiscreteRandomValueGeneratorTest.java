package com.sm;

import com.sm.profiling.SimpleProfiler;
import com.test.DiscreteRandomValue;
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
    private DiscreteRandomValueGenerator generatorToy1;
    private DiscreteRandomValueGenerator generatorToy2;
    private DiscreteRandomValueGenerator generatorToy3;
    private static final double MODELLING_ERROR = 0.01;
    private static final double ANALYTICAL_ERROR = 0.002;
    private static final int STD_RUN_COUNT = 100000;

    @Before
    public void init() {
        dt.put(new IntDiscreteValue(1), new Probability(0.1));
        dt.put(new IntDiscreteValue(2), new Probability(0.1));
        dt.put(new IntDiscreteValue(3), new Probability(0.5));
        dt.put(new IntDiscreteValue(4), new Probability(0.2));
        dt.put(new IntDiscreteValue(5), new Probability(0.1));

        generator1 = DiscreteRandomValueGenerator.get(dt);
        generator3 = DiscreteRandomValueGenerator.get(dt);

        DistributionTable dt2 = new DistributionTable();
        dt2.put(new IntDiscreteValue(5), new Probability(0.3));
        dt2.put(new IntDiscreteValue(9), new Probability(0.5));
        dt2.put(new IntDiscreteValue(10), new Probability(0.2));
        generator2 = DiscreteRandomValueGenerator.get(dt2);

        DistributionTable dtToy1 = new DistributionTable();
        dtToy1.put(new IntDiscreteValue(1), new Probability(0.1));
        dtToy1.put(new IntDiscreteValue(2), new Probability(0.7));
        dtToy1.put(new IntDiscreteValue(3), new Probability(0.2));
        DistributionTable dtToy2 = new DistributionTable();
        dtToy2.put(new IntDiscreteValue(7), new Probability(0.4));
        dtToy2.put(new IntDiscreteValue(8), new Probability(0.6));
        DistributionTable dtToy3 = new DistributionTable();
        dtToy3.put(new IntDiscreteValue(21), new Probability(0.2));
        dtToy3.put(new IntDiscreteValue(14), new Probability(0.8));
        generatorToy1 = new DiscreteRandomValueGenerator(dtToy1);
        generatorToy2 = new DiscreteRandomValueGenerator(dtToy2);
        generatorToy3 = new DiscreteRandomValueGenerator(dtToy3);

    }

    @Test
    public void test_Get_Next() throws Exception {
        Experiment experiment = new Experiment(generator1);
        experiment.run(STD_RUN_COUNT);

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

        double error = MODELLING_ERROR;

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
        ce.run(STD_RUN_COUNT);

        DistributionFunction df = ModellingDF.get().createAndParallelism(ce);

        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("Compatible events distribution table:\n" + dt_);

        double error = MODELLING_ERROR;
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
        new CompatibleExperiments(experiment1, experiment2).run(STD_RUN_COUNT);

        // analytical
        DistributionFunction df1 = ModellingDF.get().createSingle(experiment1);
        DistributionFunction df2 = ModellingDF.get().createSingle(experiment2);
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(df1, df2);

        DistributionFunction dfAnalytical = AnalyticalDF.get().createAND(cdf);


        // modelling
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        DistributionFunction dfModelling = ModellingDF.get().createAndParallelism(ce);

        assertEquals(dfModelling, dfAnalytical, ANALYTICAL_ERROR);
    }

    @Test
    public void test_compatible3_distribution_functions_analytical() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generatorToy2);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3).run(STD_RUN_COUNT);

        // analytical
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                generator1.getDistributionFunction(),
                generator2.getDistributionFunction(),
                generatorToy2.getDistributionFunction());

        DistributionFunction dfAnalytical = AnalyticalDF.get().createAND(cdf);

        // modelling
        DistributionFunction dfModelling = ModellingDF.get().createAndParallelism(ce);
        System.out.println("AND3 distribution table (modelling):\n" + dfModelling.getDistributionTable());
        System.out.println("AND3 distribution table (analytical):\n" + dfAnalytical.getDistributionTable());

        assertEquals(dfModelling, dfAnalytical, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_OR_parallelism() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator1);
//        Experiment experiment3 = new Experiment(generator3);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);
        System.out.println("OR distribution table (modelling):\n" + orParallelismDF.getDistributionTable());

        double error = MODELLING_ERROR;

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
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);
        System.out.println("OR distribution table (modelling):\n" + orParallelismDF.getDistributionTable());

        DistributionFunction df1 = ModellingDF.get().createSingle(experiment1);
        DistributionFunction df2 = ModellingDF.get().createSingle(experiment2);

        DistributionFunction modellingDF = ModellingDF.get().createOrParallelism(ce);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(df1, df2);
        DistributionFunction analyticalDF = AnalyticalDF.get().createOR(cdf);

        System.out.println("OR distribution table (analytical):\n" + analyticalDF.getDistributionTable());

        assertEquals(modellingDF, analyticalDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_OR3_parallelism() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generatorToy2);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3).run(STD_RUN_COUNT);

        DistributionFunction modellingDF = ModellingDF.get().createOrParallelism(ce);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3)

        );
        DistributionFunction analyticalDF = AnalyticalDF.get().createOR(cdf);
        System.out.println("OR3 distribution table (modelling):\n" + modellingDF.getDistributionTable());
        System.out.println("OR3 distribution table (analytical):\n" + analyticalDF.getDistributionTable());

        assertEquals(modellingDF, analyticalDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_3_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 3);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_2() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
//        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generator3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());


        double error = MODELLING_ERROR;

        assertEquals(0.00986, mnParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.0302 , mnParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.44915, mnParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
        assertEquals(0.32089, mnParallelismDF.getDistributionTable().getProbabilityInRow(3).getValue(), error);
        assertEquals(0.1899 , mnParallelismDF.getDistributionTable().getProbabilityInRow(4).getValue(), error);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_3_two_quick() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        Experiment experiment1 = new Experiment(gen1);
        Experiment experiment2 = new Experiment(gen1);
        Experiment experiment3 = new Experiment(DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98}));

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);

//        System.out.println("Measurements:\n" + ce.toString());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());


        double error = MODELLING_ERROR;

        assertEquals(0.001, mnParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.98, mnParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.02, mnParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        Experiment experiment3 = new Experiment(generatorToy2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);
//        ce.run(10000);
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMNParallelism(ce, M);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3));


        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3_two_quick() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen2 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98});
        Experiment experiment1 = new Experiment(gen1);
        Experiment experiment2 = new Experiment(gen1);
        Experiment experiment3 = new Experiment(gen2);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMNParallelism(ce, M);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3));


        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3_two_quick50_50() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 4}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen2 = DiscreteRandomValueGenerator.get(new int[]{1, 3, 4}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen3 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 4}, new double[]{0.01, 0.01, 0.98});
        Experiment experiment1 = new Experiment(gen1);
        Experiment experiment2 = new Experiment(gen2);
        Experiment experiment3 = new Experiment(gen3);

        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMNParallelism(ce, M);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3));


        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_df_builder_by_val() {
        DistributionFunctionByValueBuilder dfBuilder = new DistributionFunctionByValueBuilder();
        Experiment exp = new Experiment(generator1);
        exp.run(STD_RUN_COUNT);
        DistributionFunction df = ModellingDF.get().createSingle(exp);
        int size = df.getDistributionTable().size();
        for(int i = 0; i < size; i++) {
            DiscreteValue<Integer> dv = df.getDistributionTable().getDiscreteValueInRow(i);
            dfBuilder.add(dv, df.eval(dv));
        }
        DistributionFunction builtDF = dfBuilder.build();
        assertEquals(df, builtDF, ANALYTICAL_ERROR);
    }


    @Test
    public void test_modelling_sequence_invoke() {
        Experiment experiment1 = new Experiment(generator1);
        Experiment experiment2 = new Experiment(generator2);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2);
        ce.run(STD_RUN_COUNT);

        DistributionFunction sequenceDF = ModellingDF.get().createSequenceProcessing(ce);
        System.out.println("Sequence processing distribution table (modelling):\n" + sequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(ModellingDF.get().createSingle(experiment1)).getValue();
        double expVal2 = new ExpectedValue(ModellingDF.get().createSingle(experiment2)).getValue();
        double expVal12 = new ExpectedValue(sequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + new ExpectedValue(ModellingDF.get().createSingle(experiment1)));
        System.out.println("Expected value of the experiment 2: " + new ExpectedValue(ModellingDF.get().createSingle(experiment2)));
        System.out.println("Expected value of the sequence of2: " + new ExpectedValue(sequenceDF));

        assertEquals(expVal1 + expVal2, expVal12, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_sequence_invoke() {
        Experiment experiment1 = new Experiment(generatorToy1);
        Experiment experiment2 = new Experiment(generatorToy2);
        Experiment experiment3 = new Experiment(generatorToy3);
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(STD_RUN_COUNT);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                ModellingDF.get().createSingle(experiment1),
                ModellingDF.get().createSingle(experiment2),
                ModellingDF.get().createSingle(experiment3)
                );

        DistributionFunction analyticalSequenceDF = AnalyticalDF.get().createSequenceProcessing1(cdf);
        DistributionFunction modellingSequenceDF = ModellingDF.get().createSequenceProcessing(ce);

        System.out.println("Sequence processing distribution table (analytical):\n" + analyticalSequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(ModellingDF.get().createSingle(experiment1)).getValue();
        double expVal2 = new ExpectedValue(ModellingDF.get().createSingle(experiment2)).getValue();
        double expVal3 = new ExpectedValue(ModellingDF.get().createSingle(experiment3)).getValue();
        double expVal123 = new ExpectedValue(analyticalSequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + new ExpectedValue(ModellingDF.get().createSingle(experiment1)));
        System.out.println("Expected value of the experiment 2: " + new ExpectedValue(ModellingDF.get().createSingle(experiment2)));
        System.out.println("Expected value of the experiment 3: " + new ExpectedValue(ModellingDF.get().createSingle(experiment3)));
        System.out.println("Expected value of the sequence of3: " + new ExpectedValue(analyticalSequenceDF));

        assertEquals(expVal1 + expVal2 + expVal3, expVal123, ANALYTICAL_ERROR);
        assertEquals(modellingSequenceDF, analyticalSequenceDF, ANALYTICAL_ERROR);
    }

    private static void assertEquals(double a, double b, double error) {
        Assert.assertEquals(a + " and " + b + " are not equal with error " + error, true, Math.abs(a - b) < error);
    }

    private static boolean assertEquals(DistributionFunction df1, DistributionFunction df2, double error) {
        CompatibleDistributionFunctions<Integer> cdf = new CompatibleDistributionFunctions<Integer>(df1, df2);
//        assert df1.getDistributionTable().size() == df2.getDistributionTable().size() : "compared DFs should have the same size";
        for (DiscreteValue<Integer> dv : cdf.getDiscreteValueSet()) {
            assertEquals(df1.eval(dv), df2.eval(dv), error);
        }
        return true;
    }

}
