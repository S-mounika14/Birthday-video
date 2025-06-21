package com.birthday.video.maker.Birthday_Video.filters.library.image;

import static javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;


public class ImageEglSurface {

    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

    private int mWidth, mHeight;

    private EGL10 mEGL;
    private EGLDisplay mEGLDisplay;
    private EGLConfig mEGLConfig;
    private EGLContext mEGLContext;
    private EGLSurface mEGLSurface;
    private GL10 mGL;

    private GLSurfaceView.Renderer mRenderer;

    public ImageEglSurface(final int width, final int height) {
        try {
            mWidth = width;
            mHeight = height;

            int[] version = new int[2];
            int[] surfaceAttribList = {
                    EGL10.EGL_WIDTH, width, EGL10.EGL_HEIGHT, height, EGL10.EGL_NONE
            };

            mEGL = (EGL10) EGLContext.getEGL();
            mEGLDisplay = mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("unable to get EGL10 display");
            }

            if (!mEGL.eglInitialize(mEGLDisplay, version)) {
                mEGLDisplay = null;
                throw new RuntimeException("unable to initialize EGL14");
            }

            mEGLConfig = getConfig();
            if (mEGLConfig == null) {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
            int[] attribList = {
                    EGL_CONTEXT_CLIENT_VERSION /*EGL14.EGL_CONTEXT_CLIENT_VERSION*/, 2, EGL14.EGL_NONE
            };
            mEGLContext = mEGL.eglCreateContext(mEGLDisplay, mEGLConfig, EGL_NO_CONTEXT, attribList);
            mEGLSurface = mEGL.eglCreatePbufferSurface(mEGLDisplay, mEGLConfig, surfaceAttribList);
            mEGL.eglMakeCurrent(mEGLDisplay, mEGLSurface, mEGLSurface, mEGLContext);
            mGL = (GL10) mEGLContext.getGL();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EGLConfig getConfig() {
        int renderableType = 4; // EGL14.EGL_OPENGL_ES2_BIT;
        int[] attribList = {
                EGL10.EGL_DEPTH_SIZE, 0, EGL10.EGL_STENCIL_SIZE, 0, EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8, EGL10.EGL_BLUE_SIZE, 8, EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_RENDERABLE_TYPE, renderableType, EGL10.EGL_NONE
        };

        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];

        if (!mEGL.eglChooseConfig(mEGLDisplay, attribList, configs, configs.length, numConfigs)) {
            Log.w("ImageEglSurface", "unable to find RGB8888  EGLConfig");
            return null;
        }
        return configs[0];
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        try {
            mRenderer = renderer;
            mRenderer.onSurfaceCreated(mGL, mEGLConfig);
            mRenderer.onSurfaceChanged(mGL, mWidth, mHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawFrame() {
        try {
            if (mRenderer == null) {
                return;
            }
            mRenderer.onDrawFrame(mGL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        ByteBuffer buf = null;
        Bitmap bmp;
        try {
            buf = ByteBuffer.allocateDirect(mWidth * mHeight * 4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            GLES20.glReadPixels(0, 0, mWidth, mHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf);
            bmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            bmp.copyPixelsFromBuffer(buf);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            try {
                bmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
                bmp.copyPixelsFromBuffer(buf);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                try {
                    bmp = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
                    bmp.copyPixelsFromBuffer(buf);
                } catch (OutOfMemoryError exc) {
                    exc.printStackTrace();
                    return null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bmp;
    }

    public void release() {
        try {
            mEGL.eglDestroySurface(mEGLDisplay, mEGLSurface);
            if (mEGLDisplay != EGL10.EGL_NO_DISPLAY) {
                mEGL.eglMakeCurrent(mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_CONTEXT);
                mEGL.eglDestroyContext(mEGLDisplay, mEGLContext);
                //EGL14.eglReleaseThread();
                mEGL.eglTerminate(mEGLDisplay);
            }

            mEGLDisplay = EGL10.EGL_NO_DISPLAY;
            mEGLConfig = null;
            mEGLContext = EGL10.EGL_NO_CONTEXT;
            mEGLSurface = EGL10.EGL_NO_SURFACE;

            mWidth = mHeight = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
