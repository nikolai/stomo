package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TProcess;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:07
 */
public class BMCProcess extends AbstractActivity<TProcess> {

    public BMCProcess(TProcess o) {
        super(o);
        o.setName(Naming.process.nextName());
    }

    public BMCProcess setSequence(BMCSequence sequence) {
        getDelegate().setSequence(sequence.getDelegate());
        return this;
    }
}
