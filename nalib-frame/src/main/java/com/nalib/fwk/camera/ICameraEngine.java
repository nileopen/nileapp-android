package com.nalib.fwk.camera;

/**
 * Created by taotao on 16/7/14.
 */
public interface ICameraEngine {
    int DEFAULT_WIDTH = 720;
    int DEFAULT_HEIGHT = 1280;
    int DEFAULT_FRAME_RATE = 20;

    void setCameraEvent(ICameraEvent event);

    void openCamera(int cameraId);

    void switchCamera();

    void closeCamera();

    void switchLight(boolean isOpen);

    void destroy();

}
