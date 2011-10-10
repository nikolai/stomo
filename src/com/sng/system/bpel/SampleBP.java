package com.sng.system.bpel;

import com.sm.DistributionTable;
import com.sm.IntDiscreteValue;
import com.sm.Probability;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 17:48
 */
public class SampleBP implements BusinessProcess<String>{
    private static NamedValueGenerator valueGenerator;
    static {
        DistributionTable dt = new DistributionTable();
        dt.put(new IntDiscreteValue(200), new Probability(0.1));
        dt.put(new IntDiscreteValue(40), new Probability(0.2));
        dt.put(new IntDiscreteValue(30), new Probability(0.5));
        dt.put(new IntDiscreteValue(50), new Probability(0.1));
        dt.put(new IntDiscreteValue(100), new Probability(0.1));
        valueGenerator = new NamedValueGenerator("simpleService", dt);
    }

    public String invoke(String arg) {
        return StochasticServiceImpl.get(valueGenerator).invoke(arg);
    }
}
