package com.nalib.fwk.camera;

import android.graphics.ImageFormat;
import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by taotao on 16/7/14.
 */
public class NaCameraManager implements ICameraEngine, ICameraEvent{

    private final static String Tag = "NaCameraManager";
    private static int sThreadId = 1000;
    private static NaCameraManager sInstance;

    private HandlerThread mCameraThread;
    private Handler mCameraHanlder;

    private ICameraEvent mCameraEvent;
    private ICameraEngine mCameraEngine;

    public static NaCameraManager getInstance() {
        if (sInstance == null) {
            synchronized (NaCameraManager.class) {
                if (sInstance == null) {
                    sInstance = new NaCameraManager();
                }
            }
        }
        return sInstance;
    }

    private int getThreadId() {
        return sThreadId++;
    }

    public void init() {
        if (mCameraThread != null) {
            mCameraThread = new HandlerThread(Tag + "-" + getThreadId());
            mCameraThread.start();
            mCameraHanlder = new Handler(mCameraThread.getLooper());
        }
    }

    public void release(){
        if (mCameraThread != null){
            mCameraThread.quit();
        }
        mCameraThread = null;
        mCameraHanlder = null;
    }

    @Override
    public void setCameraEvent(ICameraEvent event) {
        this.mCameraEvent = event;
        if (mCameraEngine != null){
            mCameraEngine.setCameraEvent(this);
        }
    }

    @Override
    public void onCameraError(String errorDescription) {
        if (mCameraEvent != null){
            mCameraEvent.onCameraError(errorDescription);
        }
    }

    @Override
    public void onCameraFreezed(String errorDescription) {
        if (mCameraEvent != null){
            mCameraEvent.onCameraFreezed(errorDescription);
        }
    }

    @Override
    public void onCameraOpening(int cameraId) {
        if (mCameraEvent != null){
            mCameraEvent.onCameraOpening(cameraId);
        }
    }

    @Override
    public void onFirstFrameAvailable() {
        if (mCameraEvent != null){
            mCameraEvent.onFirstFrameAvailable();
        }
    }

    @Override
    public void onCameraClosed() {
        if (mCameraEvent != null){
            mCameraEvent.onCameraClosed();
        }
    }
}
