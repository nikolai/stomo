package com.sm.bpelmodeller.smprocessors;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.config.ConfigHelper;
import com.sm.model.Action;
import com.sm.model.ModelFactory;
import com.sm.model.Parallel;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;

import java.util.List;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 17:33
 */
public class FlowProcessor implements ActivityProcessor<TFlow> {
    private final ModelFactory mf;
    private final ConfigHelper configHelper;

    public FlowProcessor(ModelFactory mf, ConfigHelper configHelper) {
        this.mf = mf;
        this.configHelper = configHelper;
    }

    public Action processActivity(TFlow a, ActivityRunner processorFactory) {
        Parallel parallel = mf.createParallel();

        Long countOfWaitedResults = configHelper.findCountOfWaitedResults(a);
        if (countOfWaitedResults != null && countOfWaitedResults > 0) {
            parallel.setCountOfWaitedResults(countOfWaitedResults.intValue());
        }
        List<Object> activities = a.getActivity();

        for (Object av : activities){
            parallel.addStoAction(processorFactory.goAhead((TActivity) av));
        }

        return parallel;
    }
}
