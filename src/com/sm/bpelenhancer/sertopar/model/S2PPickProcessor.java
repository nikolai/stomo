package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TOnAlarmPick;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TOnMessage;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TPick;

/**
 * User: mikola
 * Date: 07.11.15
 * Time: 19:06
 */
public class S2PPickProcessor implements ActivityProcessor<TPick,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TPick a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphComplexNode<TPick> complexNode = new DependencyGraphComplexNode<>(a);
        for (TOnAlarmPick onAlarmPick : a.getOnAlarm()) {
            TActivity onA = ChildActivitySelector.getOne().selectChildSafe(onAlarmPick).getActivity();
            S2PIfProcessor.process(complexNode, activityRunner, onA);
        }

        for (TOnMessage onMessage : a.getOnMessage()) {
            TActivity onA = ChildActivitySelector.getOne().selectChild(onMessage).getActivity();
            S2PIfProcessor.process(complexNode, activityRunner, onA);
        }
        return complexNode;
    }
}
