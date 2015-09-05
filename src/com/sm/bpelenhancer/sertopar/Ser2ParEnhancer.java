package com.sm.bpelenhancer.sertopar;

import com.sm.DistributionFunction;
import com.sm.bpelenhancer.BPELEnhancer;
import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.smprocessors.DefaultProcessor;
import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.util.List;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 13:11
 */
public class Ser2ParEnhancer implements BPELEnhancer {

    @Override
    public void enhance(TProcess process, BPELEnhancer nextEnhancer, ChangeLog changeLog) {
        TSequence sequence = process.getSequence();
        ActivityRunner activityRunner = ActivityRunner.getOne();

        ActivityDependencyGraph dependencyGraph = new ActivityDependencyGraph();

        activityRunner.registerActivityProcessor(TReceive.class, new S2PReceiveProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TSequence.class, new S2PSequenceProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TAssign.class, new S2PAssignProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TInvoke.class, new S2PInvokeProcessor(dependencyGraph));
        activityRunner.registerActivityProcessor(TReply.class, new S2ReplyProcessor(dependencyGraph));

        activityRunner.goAhead(sequence);
        !!!!! остановился тут!!! DependencyGraph строится правильно для примера
                Не доделано: учитывать, что зависимость может быть не на один уровень вверх
        (сейчас просмотр идет только до родителя на один уровень вверх!)
        Но это потом. Сначала надо проверить Enhancer - изменение модели по графу зависимости
                и сохранение её на диск!!!
    }
}
