package com.sm.bpelmodeller.smprocessors;

import com.sm.DistributionTable;
import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.bpelmodeller.config.ConfigHelper;
import com.sm.model.Action;
import com.sm.model.ModelFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 18:02
 */
public class DefaultProcessor implements ActivityProcessor<TActivity, Action> {
    private final ModelFactory mf;
    private final ConfigHelper configHelper;

    public DefaultProcessor(ModelFactory mf, ConfigHelper configHelper) {
        this.mf = mf;
        this.configHelper = configHelper;
    }

    public Action processActivity(TActivity a, ActivityRunner<Action> activityRunner) {
        DistributionTable<Integer> dt = configHelper.findDT(a);
        return mf.createAction(dt);
    }
}
