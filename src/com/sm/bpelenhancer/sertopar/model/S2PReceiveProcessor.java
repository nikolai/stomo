package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReceive;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:18
 */
public class S2PReceiveProcessor extends S2PAbstractProcessor implements ActivityProcessor<TReceive, DependencyGraphNode> {
    public S2PReceiveProcessor() {
        super();
    }

    @Override
    public DependencyGraphNode<TReceive> processActivity(TReceive a, ActivityRunner activityRunner) {
        DependencyGraphNode<TReceive> graphNode = new DependencyGraphNode<>(a);
        graphNode.addWriteVar(a.getVariable());
        //model.addNode(graphNode);
        return graphNode;
    }
}
