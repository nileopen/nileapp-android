package com.nalib.fwk.http;

import com.nalib.fwk.exception.NaHttpException;

/**
 * Created by taotao on 16/8/31.
 */
public interface INaCallBack {
    void onError(NaHttpException e, int id);

    void onResponse(String response, int id);
}
