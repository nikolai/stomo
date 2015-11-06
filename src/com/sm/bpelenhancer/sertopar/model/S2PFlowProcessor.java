package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;

import java.util.List;

/**
 * User: mikola
 * Date: 03.11.15
 * Time: 20:06
 */
public class S2PFlowProcessor extends S2PAbstractProcessor implements ActivityProcessor<TFlow, Action> {
    public S2PFlowProcessor(S2PEnhancingModel model) {
        super();
    }

    @Override
    public Action processActivity(TFlow flow, ActivityRunner<Action> activityRunner) {
        List<Object> flows = flow.getActivity();
//        model.addNode(new DependencyGraphNode<>(flow));
        for (Object a : flows) {
            activityRunner.goAhead((TActivity) a);
        }
        return null;
    }
}
