package com.nalib.fwk.camera;

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
                    sInstance.init();
                }
            }
        }
        return sInstance;
    }

    private int getThreadId() {
        return sThreadId++;
    }

    private void init() {
        if (mCameraThread != null) {
            mCameraThread = new HandlerThread(Tag + "-" + getThreadId());
            mCameraThread.start();
            mCameraHanlder = new Handler(mCameraThread.getLooper());
        }

        if (mCameraEngine == null) {
            mCameraEngine = new NaCameraEngine();
        }
    }

    private void release() {
        if (mCameraThread != null){
            mCameraThread.quit();
        }
        mCameraThread = null;
        mCameraHanlder = null;
        sInstance = null;
    }

    @Override
    public void setCameraEvent(ICameraEvent event) {
        this.mCameraEvent = event;
        if (mCameraEngine != null){
            mCameraEngine.setCameraEvent(this);
        }
    }

    @Override
    public void openCamera(int cameraId) {
        if (mCameraEngine != null) {
            mCameraEngine.openCamera(cameraId);
        }
    }

    @Override
    public void switchCamera() {
        if (mCameraEngine != null) {
            mCameraEngine.switchCamera();
        }
    }

    @Override
    public void closeCamera() {
        if (mCameraEngine != null) {
            mCameraEngine.closeCamera();
        }
    }

    @Override
    public void switchLight(boolean isOpen) {
        if (mCameraEngine != null) {
            mCameraEngine.switchLight(isOpen);
        }
    }

    @Override
    public void destroy() {
        if (mCameraEngine != null) {
            mCameraEngine.destroy();
        }
        mCameraEngine = null;
        mCameraEvent = null;
        release();
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

    @Override
    public void onSwicthCamera(boolean isSuccess) {
        if (mCameraEvent != null) {
            mCameraEvent.onSwicthCamera(isSuccess);
        }
    }

    @Override
    public void onSwithcLight(boolean isSuccess) {
        if (mCameraEvent != null) {
            mCameraEvent.onSwithcLight(isSuccess);
        }
    }
}
