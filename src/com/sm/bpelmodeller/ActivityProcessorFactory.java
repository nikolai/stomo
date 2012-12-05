package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

/**
 * User: mikola
 * Date: 24.11.12
 * Time: 1:03
 */
public class ActivityProcessorFactory {
    private ActivityProcessorFactory(){}
    public static ActivityProcessor getOne(){
        return new ActivityProcessor();
    }

    static interface ActivityProcessor
    void processActivity(TActivity a);
}
