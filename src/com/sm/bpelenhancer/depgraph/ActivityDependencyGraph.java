package com.sm.bpelenhancer.depgraph;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:28
 */
public class ActivityDependencyGraph {
    private final DependencyGraphNode<? extends TActivity> rootNode;

    public ActivityDependencyGraph(DependencyGraphNode<? extends TActivity> rootNode) {
        this.rootNode = rootNode;
    }

    public DependencyGraphNode<? extends TActivity> getRootNode() {
        return rootNode;
    }
}
