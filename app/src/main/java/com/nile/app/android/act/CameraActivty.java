package com.nile.app.android.act;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nalib.fwk.app.NaBaseActivity;
import com.nalib.fwk.camera.ICameraEvent;
import com.nalib.fwk.camera.ISurfaceHelper;
import com.nalib.fwk.camera.NaCameraManager;
import com.nalib.fwk.utils.NaLog;
import com.nile.app.android.R;

/**
 * Created by taotao on 16/7/21.
 */
public class CameraActivty extends NaBaseActivity {

    private static final String TAG = "CameraActivty";
    private SurfaceView sfCamera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
//        mGLSurfaceView.setEGLContextClientVersion(2);
//        mGLSurfaceView.setRenderer(new NaRenderer());
        setContentView(R.layout.activity_camera);
        sfCamera = (SurfaceView) findViewById(R.id.sfCamera);

//        GLView view = null;
        sfCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                NaCameraManager.getInstance().openCamera(0);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                NaCameraManager.getInstance().closeCamera();
            }
        });

        NaCameraManager.getInstance().setCameraEvent(new ICameraEvent() {
            @Override
            public void onCameraError(String errorDescription) {
                NaLog.d(TAG, "onCameraError error=" + errorDescription);
            }

            @Override
            public void onCameraFreezed(String errorDescription) {
                NaLog.d(TAG, "onCameraFreezed error=" + errorDescription);
            }

            @Override
            public void onCameraOpening(int cameraId) {
                NaLog.d(TAG, "onCameraOpening cameraId=" + cameraId);
            }

            @Override
            public void onFirstFrameAvailable() {
                NaLog.d(TAG, "onFirstFrameAvailable");
            }

            @Override
            public void onCameraClosed() {
                NaLog.d(TAG, "onCameraClosed");
            }

            @Override
            public void onSwicthCamera(boolean isSuccess, int cameraId) {
                NaLog.d(TAG, "onSwicthCamera isSuccess=" + isSuccess + ",cameraId=" + cameraId);
            }

            @Override
            public void onSwithcLight(boolean isSuccess) {
                NaLog.d(TAG, "onSwithcLight isSuccess" + isSuccess);
            }
        });

        NaCameraManager.getInstance().setSurfaceHelper(new ISurfaceHelper() {
            @Override
            public SurfaceTexture getSurfaceTexture() {
                NaLog.d(TAG, "getSurfaceTexture");
                return null;
            }

            @Override
            public SurfaceHolder getSurfaceHolder() {
                NaLog.d(TAG, "getSurfaceHolder");
                return sfCamera.getHolder();
            }

            @Override
            public void onPreviewFrame(byte[] data, int width, int height, int orientation, long captureTimeNs) {
                int len = (data == null) ? 0 : data.length;
                NaLog.d(TAG, "onPreviewFrame data.len=" + len + ",width=" + width + ", height=" + height
                        + ",orientation=" + orientation + ",captureTimeNs=" + captureTimeNs);
            }
        });
    }

//    private class NaRenderer implements GLSurfaceView.Renderer {
//        @Override
//        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//            GLES20.glClearColor(0.9f, 0.2f, 0.2f, 1.0f);
//            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        }
//
//        @Override
//        public void onSurfaceChanged(GL10 gl, int width, int height) {
//
//        }
//
//        @Override
//        public void onDrawFrame(GL10 gl) {
//
//        }
//    }
}
