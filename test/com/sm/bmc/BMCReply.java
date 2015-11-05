package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.TReply;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 16:55
 */
public class BMCReply extends AbstractActivity<TReply> {
    public BMCReply(TReply o) {
        super(o);
        o.setName(Naming.reply.nextName());
    }

    @Override
    public AbstractActivity<TReply> setReadVars(String... vars) {
        super.setWriteVars(vars);
        if (!getReadVars().isEmpty()) {
            getDelegate().setVariable(getReadVars().get(0));
        }
        return this;
    }
}
