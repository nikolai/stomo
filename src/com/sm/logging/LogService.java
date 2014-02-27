package com.sm.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 18:38
 */
public class LogService extends Logger{
    private Level curLogLevel = Level.INFO;
    private final ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });
    private StringBuffer sb = new StringBuffer();
    private static LogService instance = new LogService();

    private LogService() {
        super(LogService.class.getSimpleName(), null);
    }
    public static LogService get() {
        return instance;
    }

    public void setCurLogLevel(Level curLogLevel) {
        this.curLogLevel = curLogLevel;
    }

    public void stop() {
        exec.shutdown();
        try {
            exec.awaitTermination(35, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(sb);
    }
    public void log(String msg) {
        logBase(msg, Level.INFO);
    }

    @Override
    public void log(LogRecord record) {
        logBase(record.getMessage(), record.getLevel());
    }

    private void logBase(String msg, Level level) {
        if (level.intValue() >= curLogLevel.intValue()) {
//            System.out.println(formatMsg(msg));
            try {
                exec.execute(new WriteTask(msg));
            } catch (RejectedExecutionException ignored) { }
        }
    }

    public void debug(String msg) {
        logBase(msg, Level.FINE);
    }

    public void severe(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logBase(sw.toString(), Level.SEVERE);
    }


    private class WriteTask implements Runnable {
        private final String msg;

        public WriteTask(String msg) {
            this.msg = "[" + Thread.currentThread().getName() + "] " + msg;
        }

        public void run() {
            String message = new Date() + ": " + msg + "\n";
            sb.append(message);
        }
    }

    private String formatMsg(String msg) {
        return new Date() + ": " + "[" + Thread.currentThread().getName() + "] " + msg;
    }
}