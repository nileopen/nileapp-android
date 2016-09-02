package com.nalib.fwk.http;

import java.util.Map;

/**
 * Created by taotao on 16/8/31.
 */
public interface INaParams {
    void addParam(String key, String value);

    Map<String, String> getParams();
}
