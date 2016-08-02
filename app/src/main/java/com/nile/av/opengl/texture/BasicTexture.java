package com.nile.av.opengl.texture;

import com.nile.av.opengl.glrenderer.GLCanvas;

import java.util.WeakHashMap;

/**
 * Created by taotao on 16/8/1.
 */
public abstract class BasicTexture {
    public static final int FORMAT_RGB = 0;
    public static final int FORMAT_YUV = 1;
    protected static final int UNSPECIFIED = -1;
    protected static final int STATE_UNLOADED = 0;
    protected static final int STATE_LOADED = 1;
    protected static final int STATE_ERROR = -1;
    private static final String TAG = "BasicTexture";
    private static final int MAX_TEXTURE_SIZE = 4096;
    private static WeakHashMap<BasicTexture, Object> sAllTextures = new WeakHashMap();
    private static ThreadLocal<Class<BasicTexture>> sInFinalizer = new ThreadLocal();
    protected int[] mId;
    protected int mState;
    protected int mWidth;
    protected int mHeight;
    protected int mTextureWidth;
    protected int mTextureHeight;
    protected GLCanvas mCanvasRef;
    int mLeft;
    int mTop;
}
