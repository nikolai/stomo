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

        ActivityRunner<DependencyGraphNode> activityRunner = ActivityRunner.getOne();
        S2PEnhancingModel model = new S2PEnhancingModel();

        activityRunner.registerActivityProcessor(TSequence.class, new S2PSequenceProcessor());
        activityRunner.registerActivityProcessor(TReceive.class, new S2PReceiveProcessor());
//        activityRunner.registerActivityProcessor(TFlow.class, new S2PFlowProcessor(model));
        activityRunner.registerActivityProcessor(TAssign.class, new S2PAssignProcessor());
        activityRunner.registerActivityProcessor(TInvoke.class, new S2PInvokeProcessor());
        activityRunner.registerActivityProcessor(TReply.class, new S2PReplyProcessor());
        activityRunner.registerActivityProcessor(TScope.class, new S2PScopeProcessor());

        // build dependency graph
        DependencyGraphSequenceComplexNode root = (DependencyGraphSequenceComplexNode) activityRunner.goAhead(sequence);

        S2PModifier modifier = new S2PModifier();
        modifier.modify(process, changeLog, root, this);

        // TODO:
//        проверить, если уже был flow, то его не надо переделывать!

        // TODO:
//                Не доделано: учитывать, что зависимость может быть не на один уровень вверх
//        (сейчас просмотр идет только до родителя на один уровень вверх!)
    }
}
