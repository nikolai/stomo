package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReceive;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:18
 */
public class S2PReceiveProcessor extends S2PAbstractProcessor implements ActivityProcessor<TReceive, Action> {
    public S2PReceiveProcessor(SequenceDependencyGraph dependencyGraph) {
        super(dependencyGraph);
    }

    @Override
    public Action processActivity(TReceive a, ActivityRunner activityRunner) {
        DependencyGraphNode<TReceive> graphNode = new DependencyGraphNode<>(a);
        graphNode.addWriteVar(a.getVariable());
        dependencyGraph.addNode(graphNode);
        return null;
    }
}
