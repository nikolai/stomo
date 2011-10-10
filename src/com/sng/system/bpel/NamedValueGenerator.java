package com.sng.system.bpel;

import com.sm.DiscreteRandomValueGenerator;
import com.sm.DistributionTable;
import com.test.DiscreteRandomValue;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 17:42
 */
public class NamedValueGenerator extends DiscreteRandomValueGenerator{
    private final String name;

    public NamedValueGenerator(String name, DistributionTable distributionTable) {
        super(distributionTable);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
