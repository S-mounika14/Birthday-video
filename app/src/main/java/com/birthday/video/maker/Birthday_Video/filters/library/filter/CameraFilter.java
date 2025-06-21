package com.birthday.video.maker.Birthday_Video.filters.library.filter;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.birthday.video.maker.Birthday_Video.filters.library.gles.GlUtil;
import com.birthday.video.maker.R;

import java.nio.FloatBuffer;

public class CameraFilter extends AbstractFilter implements IFilter {

    int mProgramHandle;
    private int maPositionLoc;
    private int muMVPMatrixLoc;
    private int muTexMatrixLoc;
    private int maTextureCoordLoc;
    private int mTextureLoc;

    private int mIncomingWidth, mIncomingHeight;

    CameraFilter(Context applicationContext) {
        try {
            mProgramHandle = createProgram(applicationContext);
            if (mProgramHandle == 0) {
                throw new RuntimeException("Unable to create program");
            }
            getGLSLValues();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTextureTarget() {
        return GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
    }

    @Override
    public void setTextureSize(int width, int height) {
        try {
            if (width == 0 || height == 0) {
                return;
            }
            if (width == mIncomingWidth && height == mIncomingHeight) {
                return;
            }
            mIncomingWidth = width;
            mIncomingHeight = height;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int createProgram(Context applicationContext) {
        return GlUtil.createProgram(applicationContext, R.raw.vertex_shader,
                R.raw.fragment_shader_ext);
    }

    @Override
    protected void getGLSLValues() {
        try {
            mTextureLoc = GLES20.glGetUniformLocation(mProgramHandle, "uTexture");
            maPositionLoc = GLES20.glGetAttribLocation(mProgramHandle, "aPosition");
            muMVPMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uMVPMatrix");
            muTexMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "uTexMatrix");
            maTextureCoordLoc = GLES20.glGetAttribLocation(mProgramHandle, "aTextureCoord");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDraw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex,
                       int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix,
                       FloatBuffer texBuffer, int textureId, int texStride) {

        try {
            GlUtil.checkGlError("draw start");

            useProgram();

            bindTexture(textureId);

            //runningOnDraw();

            bindGLSLValues(mvpMatrix, vertexBuffer, coordsPerVertex, vertexStride, texMatrix, texBuffer,
                    texStride);

            drawArrays(firstVertex, vertexCount);

            unbindGLSLValues();

            unbindTexture();

            disuseProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void useProgram() {
        try {
            GLES20.glUseProgram(mProgramHandle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //GlUtil.checkGlError("glUseProgram");
    }

    @Override
    protected void bindTexture(int textureId) {
        try {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(getTextureTarget(), textureId);
            GLES20.glUniform1i(mTextureLoc, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void bindGLSLValues(float[] mvpMatrix, FloatBuffer vertexBuffer, int coordsPerVertex,
                                  int vertexStride, float[] texMatrix, FloatBuffer texBuffer, int texStride) {

        try {
            GLES20.glUniformMatrix4fv(muMVPMatrixLoc, 1, false, mvpMatrix, 0);
            GLES20.glUniformMatrix4fv(muTexMatrixLoc, 1, false, texMatrix, 0);
            GLES20.glEnableVertexAttribArray(maPositionLoc);
            GLES20.glVertexAttribPointer(maPositionLoc, coordsPerVertex, GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);
            GLES20.glEnableVertexAttribArray(maTextureCoordLoc);
            GLES20.glVertexAttribPointer(maTextureCoordLoc, 2, GLES20.GL_FLOAT, false, texStride,
                    texBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void drawArrays(int firstVertex, int vertexCount) {
        try {
            GLES20.glClearColor(0f, 0f, 0f, 1f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, firstVertex, vertexCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unbindGLSLValues() {
        try {
            GLES20.glDisableVertexAttribArray(maPositionLoc);
            GLES20.glDisableVertexAttribArray(maTextureCoordLoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unbindTexture() {
        GLES20.glBindTexture(getTextureTarget(), 0);
    }

    @Override
    protected void disuseProgram() {
        GLES20.glUseProgram(0);
    }

    @Override
    public void releaseProgram() {
        try {
            GLES20.glDeleteProgram(mProgramHandle);
            mProgramHandle = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
