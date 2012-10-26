package com.sng.bpel.bpengine.impl;

import com.sng.bpel.bpengine.Activity;
import com.sng.bpel.bpengine.ActivityExecutionException;
import com.sng.bpel.bpengine.BpelProcess;

/**
 * User: mikola
 * Date: 12.10.12
 * Time: 16:42
 */
public class ActivityFactory {
    BpelProcess createBpelProcess(String name) {
        return new BpelProcessImpl(name);
    }
}
