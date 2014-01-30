package com.sng.bpel.bpel;

import com.sm.logging.LogService;

import java.util.concurrent.Callable;

/**
* User: smirnov-n
* Date: 11.10.11
* Time: 15:49
*/
public class InvokeBPTask implements Callable<Object> {
    private final int i;

    public InvokeBPTask(int i) {
        this.i = i;
    }

    public Object call() throws Exception {
        try {
            String count = i + "";
            String res = new SampleBP().invoke(count);
            LogService.get().log(res);
            return res;
        } catch (Exception e) {
            LogService.get().severe(e);
            throw e;
        }
    }
}
