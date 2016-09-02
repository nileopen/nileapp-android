package com.nalib.fwk.http;

/**
 * Created by taotao on 16/8/31.
 */
public class NaHttpRequest implements INaRequest {
    private String mUrl;
    private NaHttpType mType;
    private INaParams mParams;
    private INaHeaders mHeaders;
    private INaCallBack mCallBack;
    private int mId;

    public NaHttpRequest(String url, NaHttpType type, INaCallBack callback, INaParams params, INaHeaders headers) {
        this.mUrl = url;
        this.mType = type;
        this.mParams = params;
        this.mHeaders = headers;
        this.mCallBack = callback;
    }

    public NaHttpRequest(String url, NaHttpType type, INaCallBack callback) {
        this(url, type, callback, null, null);
    }

    public NaHttpRequest(String url, NaHttpType type) {
        this(url, type, null, null, null);
    }

    @Override
    public NaHttpType getType() {
        return mType;
    }

    @Override
    public void setType(NaHttpType type) {
        mType = type;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public INaParams getParams() {
        return mParams;
    }

    @Override
    public void setParams(INaParams params) {
        mParams = params;
    }

    @Override
    public INaHeaders getHeaders() {
        return mHeaders;
    }

    @Override
    public void setHeaders(INaHeaders headers) {
        mHeaders = headers;
    }

    @Override
    public INaCallBack getCallBack() {
        return mCallBack;
    }

    @Override
    public void setCallBack(INaCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }
}
