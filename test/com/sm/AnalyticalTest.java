package com.sm;

import com.sm.util.GeneratorProvider;
import static com.sm.util.AssertUtil.*;
import static com.sm.util.AssertUtil.assertEquals;

import org.junit.Test;

/**
 * User: smirnov-n
 * Date: 08.09.11
 * Time: 20:13
 */
public class AnalyticalTest {
    private final GeneratorProvider gp = GeneratorProvider.get();
    private final RunExperimentService runService = RunExperimentService.get();

    @Test
    public void test_alternative_markov_chain2_analytical() {
        Probability[] alternativeProbs = new Probability[]{new Probability(0.2), new Probability(0.8)};
        // modelling
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(), gp.g(2).getDistributionFunction());

        DistributionFunction dfModelling = ModellingDF.get().createAlternative(cdf, alternativeProbs);
        System.out.println("Alternative distribution table (modelling):\n" + dfModelling.getDistributionTable());

        // analytical
        DistributionFunction dfAnalytical = AnalyticalDF.get().createAlternative(cdf, alternativeProbs);
        System.out.println("Alternative distribution table (analytical):\n" + dfAnalytical.getDistributionTable());

        assertEquals(dfModelling, dfAnalytical, ANALYTICAL_ERROR);
    }

    @Test
    public void test_compatible_distribution_functions_analytical() {
        // analytical
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(), gp.g(2).getDistributionFunction());

        DistributionFunction dfAnalytical = AnalyticalDF.get().createAND(cdf);

        // modelling
        DistributionFunction dfModelling = ModellingDF.get().createAND(cdf);

        assertEquals(dfModelling, dfAnalytical, ANALYTICAL_ERROR);
    }

    @Test
    public void test_compatible3_distribution_functions_analytical() {
        // analytical
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction(),
                gp.g(5).getDistributionFunction());

        DistributionFunction dfAnalytical = AnalyticalDF.get().createAND(cdf);

        // modelling
        DistributionFunction dfModelling = ModellingDF.get().createAND(cdf);
        System.out.println("AND3 distribution table (modelling):\n" + dfModelling.getDistributionTable());
        System.out.println("AND3 distribution table (analytical):\n" + dfAnalytical.getDistributionTable());

        assertEquals(dfModelling, dfAnalytical, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_OR_parallelism() {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction());

        DistributionFunction modellingDF = ModellingDF.get().createOR(cdf);
        DistributionFunction analyticalDF = AnalyticalDF.get().createOR(cdf);

        System.out.println("OR distribution table (modelling):\n" + modellingDF.getDistributionTable());
        System.out.println("OR distribution table (analytical):\n" + analyticalDF.getDistributionTable());

        assertEquals(modellingDF, analyticalDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_OR3_parallelism() {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction(),
                gp.g(5).getDistributionFunction());

        DistributionFunction modellingDF = ModellingDF.get().createOR(cdf);
        DistributionFunction analyticalDF = AnalyticalDF.get().createOR(cdf);

        System.out.println("OR3 distribution table (modelling):\n" + modellingDF.getDistributionTable());
        System.out.println("OR3 distribution table (analytical):\n" + analyticalDF.getDistributionTable());

        assertEquals(modellingDF, analyticalDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3() {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction(),
                gp.g(5).getDistributionFunction());

        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMN(cdf, M);
        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);

        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_3_of_4() {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction(),
                gp.g(5).getDistributionFunction(),
                gp.g(3).getDistributionFunction());

        int M = 3;
        DistributionFunction mnModellingDF = ModellingDF.get().createMN(cdf, M);
        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);

        System.out.println("MN (3 of 4) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (3 of 4) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_4() {
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gp.g(1).getDistributionFunction(),
                gp.g(2).getDistributionFunction(),
                gp.g(5).getDistributionFunction(),
                gp.g(3).getDistributionFunction());
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMN(cdf, M);
        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);

        System.out.println("MN (2 of 4) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 4) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_analytical_MN_parallelism_2_of_3_two_quick() {
        DiscreteRandomValueGenerator gen1 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen2 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.98, 0.01});
        DiscreteRandomValueGenerator gen3 = DiscreteRandomValueGenerator.get(new int[]{1, 2, 3}, new double[]{0.01, 0.01, 0.98});
        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                gen1.getDistributionFunction(),
                gen2.getDistributionFunction(),
                gen3.getDistributionFunction());
        int M = 2;
        DistributionFunction mnModellingDF = ModellingDF.get().createMN(cdf, M);
        DistributionFunction mnParallelismDF = AnalyticalDF.get().createMN(cdf, M);

        System.out.println("MN (2 of 3) distribution table (modelling):\n" + mnModellingDF.getDistributionTable());
        System.out.println("MN (2 of 3) distribution table (analytical):\n" + mnParallelismDF.getDistributionTable());

        assertEquals(mnModellingDF, mnParallelismDF, ANALYTICAL_ERROR);
    }

    @Test
    public void test_df_builder_by_val() {
        DistributionFunctionByValueBuilder dfBuilder = new DistributionFunctionByValueBuilder();
        Experiment exp = new Experiment(gp.g(1));
        exp.run(ModellingDF.STD_RUN_COUNT);
        DistributionFunction df = exp.getDF();
        int size = df.getDistributionTable().size();
        for(int i = 0; i < size; i++) {
            DiscreteValue<Integer> dv = df.getDistributionTable().getDiscreteValueInRow(i);
            dfBuilder.add(dv, df.eval(dv));
        }
        DistributionFunction builtDF = dfBuilder.build();
        assertEquals(df, builtDF, ANALYTICAL_ERROR);
    }


    @Test
    public void test_analytical_sequence_invoke() {
        Experiment experiment1 = new Experiment(gp.g(4));
        Experiment experiment2 = new Experiment(gp.g(5));
        Experiment experiment3 = new Experiment(gp.g(6));
        CompatibleExperiments ce = new CompatibleExperiments(experiment1, experiment2, experiment3);
        ce.run(ModellingDF.STD_RUN_COUNT);

        CompatibleDistributionFunctions cdf = new CompatibleDistributionFunctions(
                experiment1.getDF(),
                experiment2.getDF(),
                experiment3.getDF()
                );

        DistributionFunction analyticalSequenceDF = AnalyticalDF.get().createSequenceProcessing(cdf);
        DistributionFunction modellingSequenceDF = ModellingDF.get().createSequenceProcessing(cdf);

        System.out.println("Sequence processing distribution table (analytical):\n" + analyticalSequenceDF.getDistributionTable());
        double expVal1 = new ExpectedValue(experiment1.getDF()).getValue();
        double expVal2 = new ExpectedValue(experiment2.getDF()).getValue();
        double expVal3 = new ExpectedValue(experiment3.getDF()).getValue();
        double expVal123 = new ExpectedValue(analyticalSequenceDF).getValue();

        System.out.println("Expected value of the experiment 1: " + new ExpectedValue(experiment1.getDF()));
        System.out.println("Expected value of the experiment 2: " + new ExpectedValue(experiment2.getDF()));
        System.out.println("Expected value of the experiment 3: " + new ExpectedValue(experiment3.getDF()));
        System.out.println("Expected value of the sequence of3: " + new ExpectedValue(analyticalSequenceDF));

        assertEquals(expVal1 + expVal2 + expVal3, expVal123, ANALYTICAL_ERROR);
        assertEquals(modellingSequenceDF, analyticalSequenceDF, ANALYTICAL_ERROR);
    }

}
