package com.nalib.fwk.exception;

/**
 * Created by taotao on 16/7/15.
 */
public class NaException extends Throwable {
    public NaException(String detailMessage) {
        super(detailMessage);
    }

    public NaException(Throwable cause) {
        super(cause);
    }
}
