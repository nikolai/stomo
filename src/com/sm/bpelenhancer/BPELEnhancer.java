package com.sm.bpelenhancer;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 16:36
 */
public interface BPELEnhancer {
    void enhance(TProcess process, ChangeLog changeLog);
}
