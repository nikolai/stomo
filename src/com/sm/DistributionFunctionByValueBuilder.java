package com.sm;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: smirnov-n
 * Date: 21.09.11
 * Time: 18:17
 */
public class DistributionFunctionByValueBuilder {
    private final SortedMap<DiscreteValue, Probability> distributionFunction = new TreeMap<DiscreteValue, Probability>();

    public DistributionFunctionByValueBuilder add(DiscreteValue dv, double dfValue) {
        distributionFunction.put(dv, new Probability(dfValue));
        return this;
    }
    public DistributionFunction build() {
        assert distributionFunction.size() > 0;

        Iterator<DiscreteValue> dvIt = distributionFunction.keySet().iterator();
        DiscreteValue firstDV = dvIt.next();
        DistributionTable dt = new DistributionTable();
        dt.put(firstDV, distributionFunction.get(firstDV));

        double previousEvaluationOfDF = 0;
        while(dvIt.hasNext()) {
            DiscreteValue curDV = dvIt.next();
            Probability p = new Probability(previousEvaluationOfDF + distributionFunction.get(curDV).getValue());
            dt.put(curDV, p);

            previousEvaluationOfDF = p.getValue();
        }

        return DistributionFunction.createByTable(dt);
    }
}
