package com.sm.bpelenhancer.sertopar.model;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

/**
 * User: mikola
 * Date: 06.11.15
 * Time: 19:52
 */
public class DependencyGraphSequenceComplexNode extends DependencyGraphComplexNode<TSequence> {
    public DependencyGraphSequenceComplexNode(TSequence activity) {
        super(activity);
    }
}
