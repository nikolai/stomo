package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.ChildActivitySelector;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TElseif;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TIf;

/**
 * User: mikola
 * Date: 07.11.15
 * Time: 18:53
 */
public class S2PIfProcessor implements ActivityProcessor<TIf,DependencyGraphNode> {
    @Override
    public DependencyGraphNode processActivity(TIf a, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphComplexNode<TIf> complexNode = new DependencyGraphComplexNode<>(a);
        complexNode.addReadVar(S2PAssignProcessor.extractVarName(a.getCondition().getContent()));

        TActivity thenA = ChildActivitySelector.getOne().selectChildSafe(a).getActivity();
        process(complexNode, activityRunner, thenA);
        if (a.getElse() != null) {
            TActivity elseA = ChildActivitySelector.getOne().selectChildSafe(a.getElse()).getActivity();
            process(complexNode, activityRunner, elseA);
        }
        for (TElseif elseif : a.getElseif()) {
            TActivity elseifA = ChildActivitySelector.getOne().selectChildSafe(elseif).getActivity();
            process(complexNode, activityRunner, elseifA);
        }
        return complexNode;
    }

    public static <T extends TActivity> void process(DependencyGraphComplexNode<T> complexNode,
                         ActivityRunner<DependencyGraphNode> activityRunner,
                         TActivity kid) {
        if (kid!= null) {
            DependencyGraphNode thenNode = activityRunner.goAhead(kid);
            complexNode.addContainedElement(thenNode);
        }
    }
}
