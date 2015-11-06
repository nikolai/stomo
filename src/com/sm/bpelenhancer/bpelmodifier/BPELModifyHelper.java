package com.sm.bpelenhancer.bpelmodifier;

import com.sm.bpelenhancer.Naming;
import com.sm.bpelenhancer.finder.ActivityFinder;
import com.sm.bpelenhancer.finder.FinderResult;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 21:00
 */
public class BPELModifyHelper {
    private final TProcess process;
    private final ObjectFactory factory = new ObjectFactory();
    private Object buffer;
    private ActivityFinder finder;

    private BPELModifyHelper(TProcess process){
        this.process = process;
        finder = ActivityFinder.getOne(process);
    }
    public static BPELModifyHelper getOne(TProcess process) {
        return new BPELModifyHelper(process);
    }

    public TFlow createTFlow() {
        String name = Naming.flow.nextName();
        TFlow f = factory.createTFlow();
        f.setName(name);
        return f;
    }

    public TSequence createTSequence() {
        String name = Naming.sequence.nextName();
        TSequence f = factory.createTSequence();
        f.setName(name);
        return f;
    }

    public <T extends TActivity> void remove(T nodeValue) {
        FinderResult result = finder.find(nodeValue.getName());
        if (result == null || result.getAct() == null) {
            throw new IllegalStateException("cannot find " + nodeValue.getName() + " in the model");
        }

        Iterator<Object> iterator = result.getContainer().getActivity().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == nodeValue) {
                iterator.remove();
            }
        }
    }

    public <T extends TActivity> void cutAndPasteInFlow(List<List<T>> flowsContent, T afterThis) {
        pasteAfter(convertToFlow(flowsContent), afterThis);
    }

    public <T extends TActivity> void cutAndPasteInFlowInSequence(List<List<T>> flowsContent, TSequence seq) {
        pasteIn(convertToFlow(flowsContent), seq);
    }

    private <T extends TActivity> void pasteAfter(T pasteThis, T afterThis) {
        FinderResult result = finder.find(afterThis.getName());
        List<Object> oldList = result.getContainer().getActivity();
        List<Object> newList = new ArrayList<>(oldList.size()+1);
        Iterator<Object> iterator = oldList.iterator();
        while(iterator.hasNext()) {
            Object next = iterator.next();
            newList.add(next);
            iterator.remove();
            if (next == afterThis) {
                newList.add(pasteThis);
            }
        }
        result.getContainer().getActivity().addAll(newList);
    }

    private <T extends TActivity> void pasteIn(T pasteThis, TSequence inThis) {
        inThis.getActivity().add(pasteThis);
    }

    private <T extends TActivity> TFlow convertToFlow(List<List<T>> flowsContent) {
        TFlow flow = createTFlow();
        for (List<T> flowContent : flowsContent) {
            TActivity activityToAdd;
            if (flowContent.size() == 1) {
                remove(flowContent.get(0));
                activityToAdd = flowContent.get(0);
            } else if (flowContent.size() > 1) {
                TSequence seq = createTSequence();
                for (T activity : flowContent) {
                    remove(activity);
                    seq.getActivity().add(activity);
                }
                activityToAdd = seq;
            } else {
                throw new IllegalStateException();
            }

            flow.getActivity().add(activityToAdd);
        }
        return flow;
    }
}
