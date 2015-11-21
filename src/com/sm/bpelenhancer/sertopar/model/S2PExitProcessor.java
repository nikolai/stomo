package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TExit;

/**
 * User: mikola
 * Date: 21.11.15
 * Time: 11:58
 */
public class S2PExitProcessor implements ActivityProcessor<TExit,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TExit a, ActivityRunner<DependencyGraphNode> activityRunner) {
        return new DependencyGraphNode<>(a);
    }
}
