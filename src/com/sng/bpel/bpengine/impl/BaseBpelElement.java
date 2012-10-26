package com.sng.bpel.bpengine.impl;

import com.sng.bpel.bpengine.BpelElement;
import com.sng.bpel.bpengine.ElementContainer;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 16:30
 */
public class BaseBpelElement implements BpelElement {
    private String name;
    private ElementContainer parent;

    public BaseBpelElement(String name, ElementContainer parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ElementContainer getParent() {
        return parent;
    }
}
