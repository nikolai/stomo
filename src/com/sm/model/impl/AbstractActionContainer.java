package com.sm.model.impl;

import com.sm.CompatibleDistributionFunctions;
import com.sm.DistributionFunction;
import com.sm.model.Action;
import com.sm.model.ActionContainer;

import java.util.LinkedList;
import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:48
 */
public abstract class AbstractActionContainer implements ActionContainer {
    private final List<Action> actions = new LinkedList<Action>();
    public ActionContainer addStoAction(Action action) {
        actions.add(action);
        return this;
    }

    protected CompatibleDistributionFunctions getCompatibleDistributionFunctions(){
        List<DistributionFunction> dfs = new LinkedList<DistributionFunction>();
        for (Action a : actions){
            dfs.add(a.getDistributionFunction());
        }
        return new CompatibleDistributionFunctions(dfs.toArray(new DistributionFunction[0]));
    }
}
