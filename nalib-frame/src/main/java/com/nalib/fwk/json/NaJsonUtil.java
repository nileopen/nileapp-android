package com.nalib.fwk.json;

import com.google.gson.Gson;

/**
 * Created by taotao on 16/9/1.
 */
public class NaJsonUtil {
    public static String beanToString(Object object) throws Exception {
        return new Gson().toJson(object);
    }

    public static Object stringToBean(String str, Class cls) throws Exception {
        return new Gson().fromJson(str, cls);
    }
}
