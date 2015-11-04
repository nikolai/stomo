package com.sm.bpelmodeller.smprocessors;

import com.sm.bpelmodeller.ActivityProcessor;
import com.sm.bpelmodeller.ActivityRunner;
import com.sm.model.Action;
import com.sm.model.ModelFactory;
import com.sm.model.Sequence;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

import java.util.List;

/**
 * User: mikola
 * Date: 09.03.15
 * Time: 17:31
 */
public class SequenceProcessor implements ActivityProcessor<TSequence, Action> {
    private final ModelFactory mf;

    public SequenceProcessor(ModelFactory mf) {
        this.mf = mf;
    }

    public Action processActivity(TSequence a, ActivityRunner<Action> activityRunner) {
        Sequence mSeq = mf.createSequence();

        List<Object> activities = a.getActivity();

        for (Object av : activities) {
            mSeq.addStoAction(activityRunner.goAhead((TActivity) av));
        }

        return mSeq;
    }
}