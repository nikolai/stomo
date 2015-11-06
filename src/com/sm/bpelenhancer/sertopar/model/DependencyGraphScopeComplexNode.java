package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TScope;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 20:47
 */
public class DependencyGraphScopeComplexNode extends DependencyGraphComplexNode<TScope> {
    public DependencyGraphScopeComplexNode(TScope activity) {
        super(activity);
    }
}
