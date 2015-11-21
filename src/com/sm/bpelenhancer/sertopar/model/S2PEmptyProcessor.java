package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TEmpty;

/**
 * User: mikola
 * Date: 21.11.15
 * Time: 12:00
 */
public class S2PEmptyProcessor implements ActivityProcessor<TEmpty,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TEmpty a, ActivityRunner<DependencyGraphNode> activityRunner) {
        return new DependencyGraphNode<>(a);
    }
}
