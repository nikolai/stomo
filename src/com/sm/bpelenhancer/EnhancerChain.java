package com.sm.bpelenhancer;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:46
 */
public class EnhancerChain {
    private final List<BPELEnhancer> enhancers = new ArrayList<>();

    public EnhancerChain addEnhancer(BPELEnhancer enhancer) {
        enhancers.add(enhancer);
        return this;
    }

    public ChangeLog start(TProcess process) {
        Iterator<BPELEnhancer> it = enhancers.iterator();
        ChangeLog changeLog = new ChangeLog();
        if (it.hasNext()) {
            it.next().enhance(process, it.hasNext() ? it.next() : null, changeLog);
        }
        return changeLog;
    }
}
