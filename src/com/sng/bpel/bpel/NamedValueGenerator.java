package com.sng.bpel.bpel;

import com.sm.DiscreteRandomValueGenerator;
import com.sm.DistributionTable;

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
