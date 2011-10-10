package com.sm.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 18:38
 */
public class LogService {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private StringBuffer sb = new StringBuffer();
    private static LogService instance = new LogService();

    private LogService() {}
    public static LogService get() {
        return instance;
    }

    public void stop() throws InterruptedException {
        exec.shutdown();
        exec.awaitTermination(35, TimeUnit.SECONDS);
        System.out.print(sb);
    }
    public void log(String msg) {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException ignored) { }
    }

    public void log(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log(sw.toString());
    }


    private class WriteTask implements Runnable {
        private final String msg;

        public WriteTask(String msg) {
            this.msg = "[" + Thread.currentThread().getName() + "] " + msg;
        }

        public void run() {
            sb.append(new Date() + ": " + msg + "\n");
        }
    }
}