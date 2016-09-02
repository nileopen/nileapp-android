package com.nalib.fwk.http.okhttp;

import com.nalib.fwk.exception.NaHttpException;
import com.nalib.fwk.http.INaHttp;
import com.nalib.fwk.http.INaRequest;
import com.nalib.fwk.http.NaHttpType;
import com.nalib.fwk.utils.NaLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by taotao on 16/8/31.
 */
public class NaOkHttp implements INaHttp {
    private final static String Tag = "NaOkHttp";

    public NaOkHttp() {
        init();
    }

    private void init() {
        //okhttp config
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(Tag))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        OkHttpUtils.delete();
    }

    @Override
    public void setRequest(INaRequest request) throws NaHttpException {
        try {

            NaHttpType type = request.getType();
            switch (type) {
                case Get: {
                    get(request);
                    break;
                }
                case Post: {
                    post(request);
                    break;
                }
                default: {
                    throw new NaHttpException("NaOkHttp setRequest error: no such NaHttpType");
                }

            }
        } catch (Exception e) {
            throw new NaHttpException(e);
        }
    }

    private void post(final INaRequest request) {
        RequestCall call = OkHttpUtils
                .post()
                .params(request.getParams() != null ? request.getParams().getParams() : null)
                .headers(request.getHeaders() != null ? request.getHeaders().getHeaders() : null)
                .url(request.getUrl())
                .build();
        int id = call.getOkHttpRequest().getId();
        NaLog.d(Tag, "post id=" + id);
        request.setId(id);
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                request.getCallBack().onError(new NaHttpException(e.getMessage()), id);
            }

            @Override
            public void onResponse(String response, int id) {
                request.getCallBack().onResponse(response, id);
            }
        });
    }

    private void get(final INaRequest request) {
        RequestCall call = OkHttpUtils
                .get()
                .params(request.getParams() != null ? request.getParams().getParams() : null)
                .headers(request.getHeaders() != null ? request.getHeaders().getHeaders() : null)
                .url(request.getUrl())
                .build();
        int id = call.getOkHttpRequest().getId();
        NaLog.d(Tag, "post id=" + id);
        request.setId(id);
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                request.getCallBack().onError(new NaHttpException(e.getMessage()), id);
            }

            @Override
            public void onResponse(String response, int id) {
                request.getCallBack().onResponse(response, id);
            }
        });
    }
}
