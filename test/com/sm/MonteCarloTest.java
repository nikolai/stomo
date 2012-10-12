package com.sm;

import com.sm.util.GeneratorProvider;
import com.sm.util.RunExperimentService;
import junit.framework.Assert;
import org.junit.Test;
import static com.sm.util.AssertUtil.*;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 18:19
 */
public class MonteCarloTest {
    private static final double MODELLING_ERROR = 0.01;
    private final RunExperimentService runService = RunExperimentService.get();
    private final GeneratorProvider gp = GeneratorProvider.get();

    @Test
    public void test_Get_Next() throws Exception {
        Experiment experiment = new Experiment(gp.g(1));
        experiment.run(RunExperimentService.STD_RUN_COUNT);

//        System.out.println("experiments = " + Arrays.toString(experiment.getMeasurements().toArray()));

        DistributionFunction df = ModellingDF.get().createSingle(experiment);
        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("distribTable:\n" + dt_.toString());


        Assert.assertEquals(dt_.size(), 5);

        assertEquals(experiment.getGenerator().getDistributionFunction().getDistributionTable(), dt_, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_OR2_parallelism() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2));

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);
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
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2));

        DistributionFunction df = ModellingDF.get().createAndParallelism(ce);

        DistributionTable dt_ = df.getDistributionTable();
        System.out.println("Compatible events distribution table:\n" + dt_);

        assertEquals(dt_, new DistributionTable(new int[]{5, 9, 10}, new double[]{0.3, 0.5, 0.2}), MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2));

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_2_new() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2));

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_3_of_3() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2), gp.g(3));

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 3);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction andParallelismDF = ModellingDF.get().createAndParallelism(ce);

        // check via AND parallelism: when M=N (M=2)
        assertEquals(andParallelismDF, mnParallelismDF, MODELLING_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_2_of_3() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2), gp.g(3));

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
    public void test_modelling_MN_parallelism_1_of_3() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2), gp.g(3));

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
//        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_modelling_MN_parallelism_1_of_2() {
        CompatibleExperiments ce = runService.run(gp.g(1), gp.g(2));

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 1);
        System.out.println("MN distribution table (modelling):\n" + mnParallelismDF.getDistributionTable());

        DistributionFunction orParallelismDF = ModellingDF.get().createOrParallelism(ce);

        // check via OR parallelism: when M=1
        assertEquals(orParallelismDF, mnParallelismDF, ANALYTICAL_ERROR);
    }


    @Test
    public void test_modelling_MN_parallelism_2_of_3_two_quick() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen2 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen3 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98});

        CompatibleExperiments ce = runService.run(gen1, gen2, gen3);

        DistributionFunction mnParallelismDF = ModellingDF.get().createMNParallelism(ce, 2);
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
        CompatibleExperiments ce = runService.run(gen1, gen2);

        DistributionFunction sequenceDF = ModellingDF.get().createSequenceProcessing(ce);
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
