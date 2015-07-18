package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 20:12
 */
public class AppArgumentReaderException extends Exception {
    public AppArgumentReaderException() {
    }

    public AppArgumentReaderException(String message) {
        super(message);
    }

    public AppArgumentReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppArgumentReaderException(Throwable cause) {
        super(cause);
    }
}
