package com.sm.bpelenhancer;

import com.sm.bpelmodeller.ChildActivitySelector;
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

    public ProcessEnhancerResult runEnhancer(TProcess process) {
        ProcessEnhancerResult res = new ProcessEnhancerResult();
        ChildActivitySelector.SelectedChild child = ChildActivitySelector.getOne().selectChild(process);
        //child.getType().process()
        // serial to parallel

        return res;
    }
}
