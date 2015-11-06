package com.sm.bpelenhancer;

import com.sm.bpelenhancer.sertopar.model.SequenceDependencyGraph;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 17:01
 */
public interface ProcessModifier {
    void modify(TProcess bp, ChangeLog changeLog, SequenceDependencyGraph graph, BPELEnhancer enhancer);
}
