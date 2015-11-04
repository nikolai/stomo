package com.sm.bpelenhancer.finder;

import org.oasis_open.docs.wsbpel._2_0.process.executable.TActivity;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

/**
 * User: mikola
 * Date: 26.09.15
 * Time: 21:35
 */
public class FinderResult {
    private final TSequence container;
    private final TActivity act;

    public FinderResult(TSequence container, TActivity act) {
        this.container = container;
        this.act = act;
    }

    public TSequence getContainer() {
        return container;
    }

    public TActivity getAct() {
        return act;
    }
}
