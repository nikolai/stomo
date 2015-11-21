package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TWait;

/**
 * User: mikola
 * Date: 21.11.15
 * Time: 12:44
 */
public class S2PWaitProcessor implements ActivityProcessor<TWait,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TWait a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphNode<TWait> graphNode = new DependencyGraphNode<>(a);
        graphNode.addReadVar(S2PAssignProcessor.extractVarName(a.getFor().getContent()));
        graphNode.addReadVar(S2PAssignProcessor.extractVarName(a.getUntil().getContent()));
        return graphNode;
    }
}
