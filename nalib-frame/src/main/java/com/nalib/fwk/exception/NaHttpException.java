package com.nalib.fwk.exception;

/**
 * Created by taotao on 16/8/31.
 */
public class NaHttpException extends NaException {
    public NaHttpException(String detailMessage) {
        super(detailMessage);
    }

    public NaHttpException(Throwable cause) {
        super(cause);
    }
}
