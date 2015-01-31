package com.sm;

import com.sm.util.GeneratorProvider;
import org.junit.Before;
import org.junit.Test;

import static com.sm.util.AssertUtil.ANALYTICAL_ERROR;
import static com.sm.util.AssertUtil.assertEquals;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 18:19
 */
public class MonteCarloTest {
    private static final double MODELLING_ERROR = 0.01;
    private final RunExperimentService runService = RunExperimentService.get();
    private final GeneratorProvider gp = GeneratorProvider.get();

//    @Test
//    public void test_Get_Next() throws Exception {
//        Experiment experiment = new Experiment(gp.g(1));
//        experiment.run(RunExperimentService.STD_RUN_COUNT);
//
////        System.out.println("experiments = " + Arrays.toString(experiment.getMeasurements().toArray()));
//
//        DistributionFunction df = ModellingDF.get().createSingle(experiment);
//        DistributionTable dt_ = df.getDistributionTable();
//        System.out.println("distribTable:\n" + dt_.toString());
//
//
//        Assert.assertEquals(dt_.size(), 5);
//
//        assertEquals(experiment.getGenerator().getDistributionFunction().getDistributionTable(), dt_, MODELLING_ERROR);
//    }


    CompatibleDistributionFunctions cdf12;
    CompatibleDistributionFunctions cdf123;

    @Before
    public void before() {
        cdf12 = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(), gp.g(2).getDistributionFunction());
        cdf123 = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(), gp.g(2).getDistributionFunction(), gp.g(3).getDistributionFunction());

    }

    @Test
    public void test_modelling_markov_chain2() {
        Probability[] alternativeProbs = new Probability[]{new Probability(0.2), new Probability(0.8)};
        DistributionFunction df = ModellingDF.get().createAlternative(cdf12, alternativeProbs);

        System.out.println("Alternative distribution table (modelling):\n" + df.getDistributionTable());

        assertEquals(new DistributionTable(new int[]{1, 2, 3, 4, 5, 9, 10},
                new double[]{0.02, 0.02, 0.1, 0.04, 0.26, 0.4, 0.16}),
                df.getDistributionTable(), MODELLING_ERROR);
    }

    @Test
    public void test_modelling_OR2_parallelism() {
        DistributionFunction orParallelismDF = ModellingDF.get().createOR(cdf12);
        System.out.println("OR distribution table (modelling):\n" + orParallelismDF.getDistributionTable());

        assertEquals(new DistributionTable(new int[]{1, 2, 3, 4, 5},
                new double[]{
                    0.10039,
                    0.09983,
                    0.49877,
                    0.20167,
                    0.09934}),
                orParallelismDF.getDistributionTable(), MODELLING_ERROR);
    }

    @Test
    public void test_AND2_parallelism() {
        DistributionFunction df = ModellingDF.get().createAND(cdf12);

        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("Compatible events distribution table:\n" + dt_);

        assertEquals(dt_, new DistributionTable(new int[]{5, 9, 10}, new double[]{0.3, 0.5, 0.2}), MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf12, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAND(cdf12);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2_new() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf12, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAND(cdf12);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_3_of_3() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf123, 3);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAND(cdf123);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_3() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf123, 2);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());


        double error = MODELLING_ERROR;

        assertEquals(0.00986, mnParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.0302 , mnParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.44915, mnParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
        assertEquals(0.32089, mnParallelismDF.getDistributionTable().getProbabilityInRow(3).getValue(), error);
        assertEquals(0.1899 , mnParallelismDF.getDistributionTable().getProbabilityInRow(4).getValue(), error);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_3() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf123, 1);
//        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOR(cdf123);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_2() {
        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf12, 1);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOR(cdf12);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }


    @Test
    public void test_modelling_MN_parallelism_2_of_3_two_quick() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen2 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen3 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98});

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gen1.getDistributionFunction(),
                gen2.getDistributionFunction(),
                gen3.getDistributionFunction());

        DistributionFunction mnParallelismDF = ModellingDF.get().createMN(cdf, 2);
        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());
        double error = MODELLING_ERROR;

        assertEquals(0.001, mnParallelismDF.getDistributionTable().getProbabilityInRow(0).getValue(), error);
        assertEquals(0.98, mnParallelismDF.getDistributionTable().getProbabilityInRow(1).getValue(), error);
        assertEquals(0.02, mnParallelismDF.getDistributionTable().getProbabilityInRow(2).getValue(), error);
    }


    @Test
    public void test_modelling_sequence_invoke() {
        DiscreteRandomValueGenerator gen1 = gp.g(1);
        DiscreteRandomValueGenerator gen2 = gp.g(2);

        DistributionFunction sequenceDF = ModellingDF.get().createSequenceProcessing(cdf12);
        //System.out.println("Sequence processing distribution table (modelling):\n" + sequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(gen1.getDistributionFunction()).getValue();
        double expVal2 = new ExpectedValue(gen2.getDistributionFunction()).getValue();
        double expVal12 = new ExpectedValue(sequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + expVal1);
        System.out.println("Expected value of the experiment 2: " + expVal2);
        System.out.println("Expected value of the sequence of2: " + expVal12);

        assertEquals(expVal1 + expVal2, expVal12, MODELLING_ERROR);
    }

}
