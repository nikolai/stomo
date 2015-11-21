package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TThrow;

/**
 * User: mikola
 * Date: 07.11.15
 * Time: 19:20
 */
public class S2PThrowProcessor implements ActivityProcessor<TThrow,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TThrow a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphNode<TThrow> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(a.getFaultVariable());
        return graphNode;
    }
}
