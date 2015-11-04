package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 17:33
 */
public interface ActivityProcessor<T extends TActivity, R> {
    R processActivity(T a, ActivityRunner<R> activityRunner);
}