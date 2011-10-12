package com.sng.system.bpel;

import com.sm.logging.LogService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 18:22
 */
public class Loader {
    private final ExecutorService executorService;
    private final int executionCount;
    private int currentExecutionCount;

    private Loader(int executionCount, int clientCount) {
        this.executionCount = executionCount;
        executorService = Executors.newFixedThreadPool(clientCount);
    }

    public void start() throws InterruptedException {
        LogService.get().log("Start loading");
        List<InvokeBPTask> tasks = new ArrayList<InvokeBPTask>();

        while (currentExecutionCount < executionCount) {
            tasks.add(new InvokeBPTask(++currentExecutionCount));
        }
        executorService.invokeAll(tasks);
        LogService.get().log("End loading");
    }

    public void stop() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(35, TimeUnit.SECONDS);
        LogService.get().stop();
    }

    public static class LoaderBuilder {
        private int executionCount = 100;
        private int clientCount;

        public LoaderBuilder() {}

        public LoaderBuilder setExecutionCount(int executionCount) {
            this.executionCount = executionCount;
            return this;
        }

        public LoaderBuilder setClientCount(int clientCount) {
            this.clientCount = clientCount;
            return this;
        }

        public Loader build() {
            return new Loader(executionCount, clientCount);
        }
    }


}
