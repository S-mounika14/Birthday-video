package com.birthday.video.maker.Birthday_Video.filters.library.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;
import com.birthday.video.maker.Birthday_Video.filters.library.gles.FullFrameRect;
import com.birthday.video.maker.Birthday_Video.filters.library.gles.GlUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ImageRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private FilterManager.FilterType mCurrentFilterType;
    private FilterManager.FilterType mNewFilterType;

    private int mTextureId = GlUtil.NO_TEXTURE;
    private final float[] mSTMatrix = new float[16];

    private int mSurfaceWidth, mSurfaceHeight;
    private int mIncomingWidth, mIncomingHeight;

    private FullFrameRect mFullScreen;

    public ImageRenderer(Context context, FilterManager.FilterType filterType) {
        mContext = context;
        mCurrentFilterType = mNewFilterType = filterType;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        try {
            Matrix.setIdentityM(mSTMatrix, 0);
            mFullScreen = new FullFrameRect(FilterManager.getImageFilter(mCurrentFilterType, mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        try {
            mSurfaceWidth = width;
            mSurfaceHeight = height;
            if (gl != null) {
                gl.glViewport(0, 0, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        try {
            if (bitmap == null) {
                return;
            }

            mIncomingWidth = bitmap.getWidth();
            mIncomingHeight = bitmap.getHeight();

            float scaleHeight = mSurfaceWidth / (mIncomingWidth * 1f / mIncomingHeight * 1f);
            float surfaceHeight = mSurfaceHeight;

            mTextureId = mFullScreen.createTexture(bitmap);
            if (mFullScreen != null) {
                mFullScreen.scaleMVPMatrix(1f, scaleHeight / surfaceHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeFilter(FilterManager.FilterType filterType) {
        mNewFilterType = filterType;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        try {
            if (mTextureId == GlUtil.NO_TEXTURE) {
                Log.e("ImageRenderer", "need setImageBitmap");
                return;
            }

            if (mNewFilterType != mCurrentFilterType) {
                mFullScreen.changeProgram(FilterManager.getImageFilter(mNewFilterType, mContext));
                mCurrentFilterType = mNewFilterType;
            }

            mFullScreen.getFilter().setTextureSize(mIncomingWidth, mIncomingHeight);

            mFullScreen.drawFrame(mTextureId, mSTMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (mFullScreen != null) {
                mFullScreen.release(false);
                mFullScreen = null;
            }

            mTextureId = GlUtil.NO_TEXTURE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
