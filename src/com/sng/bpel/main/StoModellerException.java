package com.sng.bpel.main;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:15
 */
public class StoModellerException extends Exception {

    public StoModellerException() {
    }

    public StoModellerException(String message) {
        super(message);
    }

    public StoModellerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoModellerException(Throwable cause) {
        super(cause);
    }
}
