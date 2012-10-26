package com.sng.bpel.bpengine.impl;

import com.sng.bpel.bpengine.Activity;
import com.sng.bpel.bpengine.BpelProcess;

import java.util.List;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 16:25
 */
public class BpelProcessImpl extends BaseBpelElement implements BpelProcess {
    public BpelProcessImpl(String name) {
        super(name, null);
    }

    public void addActivity(Activity a) {
    }

    public List<Activity> getActivities() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
