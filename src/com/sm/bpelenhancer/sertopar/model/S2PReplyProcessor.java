package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReply;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2PReplyProcessor extends S2PAbstractProcessor implements ActivityProcessor<TReply, DependencyGraphNode> {
    public S2PReplyProcessor() {
        super();
    }

    @Override
    public DependencyGraphNode processActivity(TReply a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphNode<TReply> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(a.getVariable());
        return graphNode;
    }
}
