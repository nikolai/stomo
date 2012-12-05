package com.sm.bpelmodeller;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:38
 */
public class ModellerException extends Exception{
    public ModellerException() {
    }

    public ModellerException(String message) {
        super(message);
    }

    public ModellerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModellerException(Throwable cause) {
        super(cause);
    }
}
