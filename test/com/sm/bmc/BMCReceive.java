package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReceive;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:29
 */
public class BMCReceive extends AbstractActivity<TReceive> {

    public BMCReceive(TReceive o) {
        super(o);
        o.setName(Naming.receive.nextName());
    }

    @Override
    public AbstractActivity<TReceive> setWriteVars(String... vars) {
        super.setWriteVars(vars);
        if (!getWriteVars().isEmpty()) {
            getDelegate().setVariable(getWriteVars().get(0));
        }
        return this;
    }
}
