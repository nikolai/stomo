package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.BPELEnhancer;
import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.sertopar.model.*;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 13:11
 */
public class S2PEnhancer implements BPELEnhancer {

    @Override
    public void enhance(TProcess process, ChangeLog changeLog) {
        TSequence sequence = process.getSequence();
        if (sequence == null) {
            return;
        }

        ActivityRunner activityRunner = ActivityRunner.getOne();
        SequenceDependencyGraph dependencyGraph = new SequenceDependencyGraph();

        activityRunner.registerActivityProcessor(TReceive.class, new S2PReceiveProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TSequence.class, new S2PSequenceProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TFlow.class, new S2PFlowProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TAssign.class, new S2PAssignProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TInvoke.class, new S2PInvokeProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TReply.class, new S2PReplyProcessor(dependencyGraph));

        // build dependency graph
        activityRunner.goAhead(sequence);

        S2PModifier modifier = new S2PModifier();
        modifier.modify(process, changeLog, dependencyGraph, this);

        // TODO:
//        проверить, если уже был flow, то его не надо переделывать!

        // TODO:
//                Не доделано: учитывать, что зависимость может быть не на один уровень вверх
//        (сейчас просмотр идет только до родителя на один уровень вверх!)
    }
}
