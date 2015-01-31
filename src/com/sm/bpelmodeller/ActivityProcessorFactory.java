package com.sm.bpelmodeller;

import com.sm.DistributionTable;
import com.sm.Probability;
import com.sm.bpelmodeller.config.ConfigHelper;
import com.sm.bpelmodeller.config.xsd.StoModelConfig;
import com.sm.model.*;
import com.sm.model.impl.DefaultModelFactory;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TFlow;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TIf;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mikola
 * Date: 24.11.12
 * Time: 1:03
 */
public class ActivityProcessorFactory {
    private static ModelFactory mf = DefaultModelFactory.getDefault();
    private static StoModelConfig config;
    private static DefaultProcessor defaultProcessor = new DefaultProcessor();

    private static Map<Class<? extends TActivity>, ActivityProcessor> activityProcessorMap
            = new HashMap<Class<? extends TActivity>, ActivityProcessor>();
    static {
        activityProcessorMap.put(TSequence.class, new SequenceProcessor());
        activityProcessorMap.put(TIf.class, new IfProcessor());
        activityProcessorMap.put(TFlow.class, new FlowProcessor());
    }

    private ActivityProcessorFactory(StoModelConfig config){
        this.config = config;
    }
    public static ActivityProcessorFactory getOne(StoModelConfig config){
        return new ActivityProcessorFactory(config);
    }

    public static ConfigHelper getConfig() {
        assert config != null;
        return ConfigHelper.getOne(config);
    }

    static interface ActivityProcessor<T extends TActivity> {
        Action processActivity(T a);
    }

    public static <T extends TActivity> ActivityProcessor getActivityProcessorFor(Class<T> clazz){
        ActivityProcessor ap = activityProcessorMap.get(clazz);
        return ap != null ? ap : defaultProcessor;
    }

    public static class SequenceProcessor implements ActivityProcessor<TSequence> {
        public Action processActivity(TSequence a) {
            Sequence mSeq = mf.createSequence();

            List<Object> activities = a.getActivity();

            for (Object av : activities){
                TActivity act = (TActivity) av;
                BpelActivityType type = BpelActivityType.valueOf(act);
                Action childAction = type.process(act);
                mSeq.addStoAction(childAction);
            }

            return mSeq;
        }
    }

    public static class FlowProcessor implements ActivityProcessor<TFlow> {
        public Action processActivity(TFlow a) {
            Parallel parallel = mf.createParallel();

            Long countOfWaitedResults = getConfig().findCountOfWaitedResults(a);
            if (countOfWaitedResults != null && countOfWaitedResults > 0) {
                parallel.setCountOfWaitedResults(countOfWaitedResults.intValue());
            }
            List<Object> activities = a.getActivity();

            for (Object av : activities){
                TActivity act = (TActivity) av;
                BpelActivityType type = BpelActivityType.valueOf(act);
                Action childAction = type.process(act);
                parallel.addStoAction(childAction);
            }

            return parallel;
        }
    }

    public static class IfProcessor implements ActivityProcessor<TIf> {

        public Action processActivity(TIf a) {
            Alternative alternative = mf.createAlternative();

            // find dt and probability of branch by name
            Probability probability = getConfig().findBranchProbability(a);
            // process if true
            ChildActivitySelector.SelectedChild ifTrueBranch = ChildActivitySelector.getOne().selectChild(a);
            Action ifTrueAction = ifTrueBranch.getType().process(ifTrueBranch.getActivity());
            alternative.addStoAction(ifTrueAction, probability);

            // process else
            ChildActivitySelector.SelectedChild elseBranch = ChildActivitySelector.getOne().selectChild(a.getElse());
            Action elseAction = elseBranch.getType().process(elseBranch.getActivity());
            alternative.addStoAction(elseAction, probability.invert());
            return alternative;
        }
    }

    public static class DefaultProcessor implements ActivityProcessor<TActivity> {
        public Action processActivity(TActivity a) {
            DistributionTable<Integer> dt = getConfig().findDT(a);
            Action action = mf.createAction(dt);
            return action;
        }
    }
}
