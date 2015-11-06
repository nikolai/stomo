package com.sm.bpelenhancer;

import com.sm.bpelenhancer.sertopar.model.ActivityDependencyGraph;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 17:01
 */
public interface ProcessModifier {
    void modify(TProcess bp, ChangeLog changeLog, ActivityDependencyGraph graph, BPELEnhancer enhancer);
}
