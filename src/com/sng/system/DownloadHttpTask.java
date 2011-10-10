package com.sng.system;

import com.sng.system.util.StreamUtil;

import java.io.BufferedInputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * User: smirnov-n
 * Date: 10.10.11
 * Time: 16:48
 */
class DownloadHttpTask implements Callable<String> {
    private final URL url;

    public DownloadHttpTask(URL url) {
        this.url = url;
    }

    public String call() throws Exception {
        InetSocketAddress proxyAddress = new InetSocketAddress("squid.office.bercut.ru", 3128);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
        URLConnection urlConn = url.openConnection(proxy);
        BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
        try {
            return StreamUtil.asString(bis, "UTF-8");
        } finally {
            bis.close();
        }
    }
}
