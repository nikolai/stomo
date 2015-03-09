package com.sm.bpelmodeller;

import com.sm.model.*;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mikola
 * Date: 24.11.12
 * Time: 1:03
 */
public class ActivityRunner {
    private ActivityProcessor defaultProcessor;

    private Map<Class<? extends TActivity>, ActivityProcessor> activityProcessorMap = new HashMap<>();

    private ActivityRunner(ActivityProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

    public static ActivityRunner getOne(ActivityProcessor defaultProcessor) {
        return new ActivityRunner(defaultProcessor);
    }

    public Action goAhead(TActivity activity) {
        return getActivityProcessorFor(activity.getClass()).processActivity(activity, this);
    }

    public <T extends TActivity> void registerActivityProcessor(Class<T> activityClass, ActivityProcessor<T> processor) {
        activityProcessorMap.put(activityClass, processor);
    }

    private <T extends TActivity> ActivityProcessor getActivityProcessorFor(Class<T> clazz){
        ActivityProcessor ap = activityProcessorMap.get(clazz);
        return ap != null ? ap : defaultProcessor;
    }

}
