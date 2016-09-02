package com.nalib.fwk.http;

import com.nalib.fwk.exception.NaHttpException;

/**
 * Created by taotao on 16/8/31.
 */
public interface INaHttp {
    void setRequest(INaRequest request) throws NaHttpException;
}
