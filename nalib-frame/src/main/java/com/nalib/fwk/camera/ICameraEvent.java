package com.nalib.fwk.camera;

/**
 * Created by taotao on 16/7/14.
 */
public interface ICameraEvent {

    // Camera error handler - invoked when camera can not be opened
    // or any camera exception happens on camera thread.
    void onCameraError(String errorDescription);

    // Invoked when camera stops receiving frames
    void onCameraFreezed(String errorDescription);

    // Callback invoked when camera is opening.
    void onCameraOpening(int cameraId);

    // Callback invoked when first camera frame is available after camera is opened.
    void onFirstFrameAvailable();

    // Callback invoked when camera closed.
    void onCameraClosed();

    void onSwicthCamera(boolean isSuccess, int cameraId);

    void onSwithcLight(boolean isSuccess);

    void onZoom(int curZoom, boolean isSuccess);
}
