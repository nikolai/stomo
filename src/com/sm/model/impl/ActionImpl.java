package com.sm.model.impl;

import com.sm.DistributionFunction;
import com.sm.DistributionTable;
import com.sm.model.Action;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:40
 */
public class ActionImpl implements Action {
    private DistributionTable distributionTable;

    ActionImpl(DistributionTable dt){
        this.distributionTable = dt;
    }

    public DistributionFunction getDistributionFunction() {
        return DistributionFunction.createByTable(distributionTable);
    }
}
