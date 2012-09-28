package com.sm.util;

import com.sm.DiscreteRandomValueGenerator;
import com.sm.DistributionTable;
import com.sm.IntDiscreteValue;
import com.sm.Probability;

/**
 * User: mikola
 * Date: 13.09.12
 * Time: 20:11
 */
public class GeneratorProvider {

    private static GeneratorProvider gp = new GeneratorProvider();
    public static GeneratorProvider get() {
        return gp;
    }

    private DistributionTable dt = new DistributionTable();
    public DiscreteRandomValueGenerator generator1;
    public DiscreteRandomValueGenerator generator2;
    public DiscreteRandomValueGenerator generator3;
    public DiscreteRandomValueGenerator generatorToy1;
    public DiscreteRandomValueGenerator generatorToy2;
    public DiscreteRandomValueGenerator generatorToy3;

    public GeneratorProvider() {
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
        generatorToy1 = DiscreteRandomValueGenerator.get(dtToy1);
        generatorToy2 = DiscreteRandomValueGenerator.get(dtToy2);
        generatorToy3 = DiscreteRandomValueGenerator.get(dtToy3);
    }

    public DiscreteRandomValueGenerator g(int index) {
        switch (index) {
            case 1: return generator1;
            case 2: return generator2;
            case 3: return generator3;
            case 4: return generatorToy1;
            case 5: return generatorToy2;
            case 6: return generatorToy3;
            default: throw new IllegalArgumentException("I don't have generator " + index);
        }
    }

}
