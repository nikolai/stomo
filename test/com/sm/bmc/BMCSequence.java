package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TSequence;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:07
 */
public class BMCSequence extends AbstractActivity<TSequence> {

    public BMCSequence(TSequence o) {
        super(o);
        o.setName(Naming.sequence.nextName());
    }

    public BMCSequence add(AbstractActivity a) {
        getDelegate().getActivity().add(a.getDelegate());
        return this;
    }
}
