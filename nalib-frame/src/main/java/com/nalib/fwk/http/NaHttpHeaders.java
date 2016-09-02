package com.nalib.fwk.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taotao on 16/8/31.
 */
public class NaHttpHeaders implements INaHeaders {
    private Map<String, String> mHeaders;

    @Override
    public void addHeader(String key, String value) {
        if (mHeaders == null) {
            mHeaders = new HashMap(10);
        }
        mHeaders.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }
}
