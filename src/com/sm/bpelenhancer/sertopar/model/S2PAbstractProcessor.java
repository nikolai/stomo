package com.sm.bpelenhancer.sertopar.model;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:23
 */
public abstract class S2PAbstractProcessor {
    protected final SequenceDependencyGraph dependencyGraph;

    protected S2PAbstractProcessor(SequenceDependencyGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
    }
}
