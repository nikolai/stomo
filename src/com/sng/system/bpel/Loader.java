package com.sng.system.bpel;

import com.sm.logging.LogService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 18:22
 */
public class Loader {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private int executionsCount;

    public void start() throws InterruptedException {
        LogService.get().log("Start loading");
        List<InvokeBPTask> tasks = new ArrayList<InvokeBPTask>();

        while (executionsCount < 100) {
            tasks.add(new InvokeBPTask(++executionsCount));
        }
        executorService.invokeAll(tasks);
        LogService.get().log("End loading");
    }

    public void stop() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(35, TimeUnit.SECONDS);
        LogService.get().stop();
    }

    private class InvokeBPTask implements Callable<Object> {
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
                LogService.get().log(e);
                throw e;
            }
        }
    }
}
