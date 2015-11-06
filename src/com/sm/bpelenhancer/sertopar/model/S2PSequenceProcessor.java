package com.sm.bpelenhancer.sertopar.model;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 20.07.15
 * Time: 0:14
 */
public class S2PSequenceProcessor extends S2PAbstractProcessor implements ActivityProcessor<TSequence, DependencyGraphNode> {

    public S2PSequenceProcessor() {
        super();
    }

    @Override
    public DependencyGraphComplexNode processActivity(TSequence sequence, ActivityRunner<DependencyGraphNode> activityRunner) {
        DependencyGraphSequenceComplexNode complexNode = new DependencyGraphSequenceComplexNode(sequence);
        SequenceDependencyGraph sdg = new SequenceDependencyGraph();
        List<Object> activities = sequence.getActivity();
        for (Object a : activities) {
            DependencyGraphNode kidNode = activityRunner.goAhead((TActivity) a);
            complexNode.addContainedElement(kidNode);
            sdg.addNode(kidNode);
        }
        return complexNode;
    }
}
