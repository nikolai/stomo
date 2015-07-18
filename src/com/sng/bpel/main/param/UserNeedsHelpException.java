package com.sng.bpel.main.param;

/**
 * User: mikola
 * Date: 18.07.15
 * Time: 20:20
 */
public class UserNeedsHelpException extends AppArgumentReaderException {
    public UserNeedsHelpException() {
        super("Need to print the usage");
    }
}
