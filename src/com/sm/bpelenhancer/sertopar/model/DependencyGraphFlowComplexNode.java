package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 21:26
 */
public class DependencyGraphFlowComplexNode extends DependencyGraphComplexNode<TFlow> {
    public DependencyGraphFlowComplexNode(TFlow activity) {
        super(activity);
    }
}
