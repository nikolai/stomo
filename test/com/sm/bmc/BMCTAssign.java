package com.sm.bmc;

import com.sm.bpelenhancer.Naming;
import org.oasis_open.docs.wsbpel._2_0.process.executable.*;

import java.util.List;

/**
 * User: mikola
 * Date: 05.11.15
 * Time: 15:56
 */
public class BMCTAssign extends AbstractActivity<TAssign> {
    private final ObjectFactory of;

    protected BMCTAssign(TAssign o, ObjectFactory of) {
        super(o);
        this.of = of;
        o.setName(Naming.assign.nextName());
    }

    public BMCTAssign addCopy(String fromVar, String toVar) {
        List<TExtensibleElements> copyElems = getDelegate().getCopyOrExtensionAssignOperation();
        TCopy tCopy = of.createTCopy();
        TFrom tFrom = of.createTFrom();
        tFrom.getContent().add("$" + fromVar);
        TTo tTo = of.createTTo();
        tTo.getContent().add("$"+toVar);
        tCopy.setFrom(tFrom);
        tCopy.setTo(tTo);
        copyElems.add(tCopy);
        return this;
    }

}
