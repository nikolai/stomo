package com.sm.bpelmodeller;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mikola
 * Date: 24.11.12
 * Time: 1:03
 */
public class ActivityRunner<R> {
    private ActivityProcessor defaultProcessor;

    private Map<Class<? extends TActivity>, ActivityProcessor> activityProcessorMap = new HashMap<>();

    private ActivityRunner(ActivityProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

    public static ActivityRunner getOne() {
        return getOne(null);
    }

    public static ActivityRunner getOne(ActivityProcessor defaultProcessor) {
        return new ActivityRunner(defaultProcessor);
    }

    public <T extends TActivity> R goAhead(T activity) {
        ActivityProcessor<T, R> activityProcessorFor = (ActivityProcessor<T, R>) getActivityProcessorFor(activity.getClass());
        return activityProcessorFor.processActivity(activity, this);
    }

    public <T extends TActivity> void registerActivityProcessor(Class<T> activityClass, ActivityProcessor<T, R> processor) {
        activityProcessorMap.put(activityClass, processor);
    }

    public void setDefaultProcessor(ActivityProcessor defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

    private <T extends TActivity> ActivityProcessor<T, R> getActivityProcessorFor(Class<T> clazz){
        ActivityProcessor ap = activityProcessorMap.get(clazz);
        if (ap != null) {
            return ap;
        } else if (defaultProcessor != null) {
            return defaultProcessor;
        }
        throw new IllegalStateException("No suitable activityProcessor for activity type " + clazz);
    }

}
