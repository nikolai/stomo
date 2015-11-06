package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TInvoke;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:20
 */
public class S2PInvokeProcessor extends S2PAbstractProcessor implements ActivityProcessor<TInvoke, DependencyGraphNode> {
    public S2PInvokeProcessor() {
        super();
    }

    @Override
    public DependencyGraphNode processActivity(TInvoke a, ActivityRunner activityRunner) {
        DependencyGraphNode<TInvoke> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(a.getInputVariable());
        graphNode.addWriteVar(a.getOutputVariable());
//        model.addNode(graphNode);
        return graphNode;
    }
}
