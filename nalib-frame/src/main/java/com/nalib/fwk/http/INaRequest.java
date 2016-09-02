package com.nalib.fwk.http;

/**
 * Created by taotao on 16/8/31.
 */
public interface INaRequest {
    NaHttpType getType();

    void setType(NaHttpType type);

    String getUrl();

    void setUrl(String url);

    INaParams getParams();

    void setParams(INaParams params);

    INaHeaders getHeaders();

    void setHeaders(INaHeaders headers);

    INaCallBack getCallBack();

    void setCallBack(INaCallBack callBack);

    void setId(int id);

}
