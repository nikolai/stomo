package com.sm.bpelenhancer.finder;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 21:34
 */
public class ActivityFinder {
    private final TProcess process;
    private ActivityRunner<FinderResult> activityRunner = ActivityRunner.getOne(new DefaultRunner());
    private String searchedName;

    public static ActivityFinder getOne(TProcess process){
        return new ActivityFinder(process);
    }

    private ActivityFinder(TProcess process){
        this.process = process;

        activityRunner.registerActivityProcessor(TSequence.class, new ActivityProcessor<TSequence, FinderResult>() {
            @Override
            public FinderResult processActivity(TSequence sequence, ActivityRunner activityRunner) {
                List<Object> activities = sequence.getActivity();
                for (Object a : activities) {
                    TActivity act = (TActivity) a;
                    if (searchedName.equals(act.getName())) {
                        return new FinderResult(sequence, act);
                    }
                    FinderResult res = ActivityFinder.this.activityRunner.goAhead((TActivity) a);
                    if (res != null) {
                        return res;
                    }
                }
                return null;
            }
        });
    }

    private class DefaultRunner implements ActivityProcessor<TActivity, FinderResult> {
        @Override
        public FinderResult processActivity(TActivity a, ActivityRunner<FinderResult> activityRunner) {
            return null;
        }
    }

    public FinderResult find(String name) {
        searchedName = name;
        return activityRunner.goAhead(process.getSequence());
    }

}
