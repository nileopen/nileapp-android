package com.nalib.fwk.camera;

import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;

/**
 * Created by taotao on 16/8/17.
 */
public interface ISurfaceHelper {

    SurfaceTexture getSurfaceTexture();

    SurfaceHolder getSurfaceHolder();

    void onPreviewFrame(byte[] data, int width, int height, int orientation, long captureTimeNs);
}
