package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TInvoke;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 16:53
 */
public class BMCInvoke extends AbstractActivity<TInvoke> {
    public BMCInvoke(TInvoke o) {
        super(o);
        o.setName(Naming.invoke.nextName());
    }

    @Override
    public AbstractActivity<TInvoke> setReadVars(String... vars) {
        super.setReadVars(vars);
        if (!getReadVars().isEmpty()) {
            getDelegate().setInputVariable(getReadVars().get(0));
        }
        return this;
    }

    @Override
    public AbstractActivity<TInvoke> setWriteVars(String... vars) {
        super.setWriteVars(vars);
        if (!getWriteVars().isEmpty()) {
            getDelegate().setOutputVariable(getWriteVars().get(0));
        }
        return this;
    }
}
