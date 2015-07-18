package com.sm.bpelenhancer.sertopar;

import com.sm.bpelenhancer.BPELEnhancer;
import com.sm.bpelenhancer.ChangeLog;
import com.sm.bpelenhancer.depgraph.ActivityDependencyGraph;
import com.sm.bpelenhancer.depgraph.DependencyGraphNode;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 13:11
 */
public class Ser2ParEnhancer implements BPELEnhancer {

    @Override
    public void enhance(TProcess process, BPELEnhancer nextEnhancer, ChangeLog changeLog) {
//        остановился тут!!!
//                Построить граф зависимостей

        TSequence sequence = process.getSequence();
        List<Object> activities = sequence.getActivity();
        TActivity firstActivity = (TActivity) activities.get(0);

        DependencyGraphNode<TActivity> root = new DependencyGraphNode<>(firstActivity);
        TActivity next = (TActivity) activities.get(1);
        DependencyGraphNode<TActivity> nextNode = new DependencyGraphNode<>(next);

        // RAW
        try {
            for (String wVar : root.getWriteVars()) {
                for (String rVar : nextNode.getReadVars()) {
                    if (rVar.contains("$" + wVar)) {
                        // has RAW deps
                        root.getKids().add(nextNode);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityDependencyGraph dependencyGraph = new ActivityDependencyGraph(root);
    }
}
