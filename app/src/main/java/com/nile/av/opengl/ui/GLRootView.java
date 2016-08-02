package com.nile.av.opengl.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.nalib.fwk.utils.NaLog;
import com.nile.av.opengl.glrenderer.GLCanvas;
import com.nile.av.opengl.glrenderer.GLES11Canvas;
import com.nile.av.opengl.glrenderer.GLES20Canvas;
import com.nile.av.opengl.utils.Utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by taotao on 16/7/29.
 */
public class GLRootView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRootView";
    private static final boolean DEBUG_FPS = false;
    private static final boolean DEBUG_DRAWING_STAT = false;
    private static final int FLAG_INITIALIZED = 1;
    private static final int FLAG_NEED_LAYOUT = 2;
    protected GLView mContentView;
    long mLastRenderTime;
    private int mFrameCount;
    private long mFrameCountingStart;
    private GL11 mGL;
    private GLCanvas mCanvas;
    private int mFlags;
    private volatile boolean mRenderRequested;
    private ReentrantLock mRenderLock;
    private Condition mFreezeCondition;
    private boolean mFreeze;
    private boolean mInDownState;
    private boolean mFirstDraw;
    private Runnable mRequestRenderOnAnimationFrame;

    public GLRootView(Context context) {
        super(context);
        init(context);
    }

    public GLRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mFrameCount = 0;
        this.mFrameCountingStart = 0L;
        this.mFlags = 2;
        this.mRenderRequested = false;
        this.mRenderLock = new ReentrantLock();
        this.mFreezeCondition = this.mRenderLock.newCondition();
        this.mInDownState = false;
        this.mFirstDraw = true;
        this.mLastRenderTime = 0L;
        this.mRequestRenderOnAnimationFrame = new Runnable() {
            public void run() {
                GLRootView.this.superRequestRender();
            }
        };
        if (!this.isInEditMode()) {
            this.mFlags |= 1;
            this.setBackgroundDrawable(null);
            this.setEGLContextClientVersion(Utils.getGLVersion(context));
            if (Utils.USE_888_PIXEL_FORMAT) {
                this.setEGLConfigChooser(8, 8, 8, 0, 0, 0);
            } else {
                this.setEGLConfigChooser(5, 6, 5, 0, 0, 0);
            }

            this.setRenderer(this);
            if (Utils.USE_888_PIXEL_FORMAT) {
                this.getHolder().setFormat(3);
            } else {
                this.getHolder().setFormat(4);
            }

        }
    }

    private void superRequestRender() {
        super.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GL11 gl11 = (GL11) gl;
        if (this.mGL != null) {
            NaLog.d(TAG, "GLObject has changed from " + this.mGL + " to " + gl11);
        }
        this.mRenderLock.lock();
        try {
            this.mGL = gl11;
            int glVersion = Utils.getGLVersion(getContext().getApplicationContext());
            if (glVersion >= 2) {
                this.mCanvas = new GLES20Canvas();
            } else {
                this.mCanvas = new GLES11Canvas(gl11);
            }
//           BasicTexture.invalidateAllTextures();
        } catch (Throwable e) {

        } finally {
            this.mRenderLock.unlock();
        }
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
