package com.sng.bpel.bpel.old;

import com.sm.profiling.SimpleProfiler;

import java.net.*;
import java.util.concurrent.*;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 12:40
 */
public class MainSystemRun {
    private final ExecutorService executorService;

    public MainSystemRun () {
        executorService = Executors.newFixedThreadPool(5);
    }

    public void start() throws MalformedURLException, ExecutionException, InterruptedException {
        SimpleProfiler bench = SimpleProfiler.start("ya");
        Future<String> yaResult = executorService.submit(new DownloadHttpTask(new URL("http://ya.ru")));
        String result = yaResult.get();
        bench.finishPrint();
        System.out.println("Result:\n\n" + result);
        executorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        new MainSystemRun().start();
    }
}

