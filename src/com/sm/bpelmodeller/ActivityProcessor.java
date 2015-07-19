package com.sm.bpelmodeller;

import com.sm.model.Action;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 17:33
 */
public interface ActivityProcessor<T extends TActivity> {
    Action processActivity(T a, ActivityRunner processorFactory);
}