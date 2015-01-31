package com.sm.bpelenhancer;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 25.01.15
 * Time: 19:53
 */
public class ProcessEnhancer {

    public static ProcessEnhancer getDefault() {
        return new ProcessEnhancer();
    }

    public ProcessEnhancerResult runAnalyzer(TProcess process) {
        ProcessEnhancerResult res = new ProcessEnhancerResult();
        // serial to parallel

        return res;
    }
}
