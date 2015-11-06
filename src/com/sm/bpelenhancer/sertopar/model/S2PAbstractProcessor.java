package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelenhancer.sertopar.model.ActivityDependencyGraph;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:23
 */
public abstract class S2PAbstractProcessor {
    protected final ActivityDependencyGraph dependencyGraph;

    protected S2PAbstractProcessor(ActivityDependencyGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
    }
}
