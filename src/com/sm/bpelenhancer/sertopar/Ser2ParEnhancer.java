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
public class Ser2ParEnhancer implements BPELEnhancer {

    @Override
    public void enhance(TProcess process, ChangeLog changeLog) {
        TSequence sequence = process.getSequence();
        ActivityRunner activityRunner = ActivityRunner.getOne();

        ActivityDependencyGraph dependencyGraph = new ActivityDependencyGraph();

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
//        !!!!! остановился тут
//                генерация идет правильно.
//                теряются  namespace'ы икспасов
//        xmlns:ns0="http://xml.netbeans.org/schema/ser2par" xmlns:ns1="http://xml.netbeans.org/schema/partner1"
//
//        <assign name="Assign1">
//        <copy>
//            <from>$Ser2parOperationIn.part1/ns0:param</from>
//            <to>$Partner1OperationIn.part1/ns1:param</to>
//        </copy>
//        <copy>
//            <from>$Ser2parOperationIn.part1/ns0:param</from>
//            <to>$Partner2OperationIn.part1/ns2:param</to>
//        </copy>
//        </assign>
//
//                Написать свой патчер!
//        проверить, если уже был flow, то его не надо переделывать!

        // TODO:
//                Не доделано: учитывать, что зависимость может быть не на один уровень вверх
//        (сейчас просмотр идет только до родителя на один уровень вверх!)
    }
}
