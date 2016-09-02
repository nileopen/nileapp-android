package com.nalib.fwk.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taotao on 16/8/31.
 */
public class NaHttpParams implements INaParams {
    private Map<String, String> mParams;

    @Override
    public void addParam(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap(10);
        }
        mParams.put(key, value);
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }
}
