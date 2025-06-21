package com.birthday.video.maker.Birthday_Video.videorecord;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import com.birthday.video.maker.Birthday_Video.videorecord.recorder.Record2;
import com.birthday.video.maker.Birthday_Video.videorecord.recorder.Record3;
import com.birthday.video.maker.Birthday_Video.videorecord.recorder.Record4;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CyclicBarrier;


public class ScreenRecorder2 {
    private MediaFormat createVideoFormat;
    public interface VideoPlay {
        public void goToPlay();
    }
    private final Context context;
    private float frame_rate = 15.0f;
    private int CONSTANT1 = 0;
    private int CONSTANT2 = 4000000;
    private BufferInfo bufferInfo;
    private MediaCodec moediaCodec;
    private Record2 record;
    private HandlerThread mRecordThread = new HandlerThread("ScreenRecorder2");
    private int height;
    private CodecInputSurface2 codecInputSurface;
    private MediaMuxer mediamuxer;
    private boolean check;
    private int Constant_bindtexture = -1;
    private int Constant_mediamuxer;
    private int width;
    private CyclicBarrier mCyclicBarrier;
    private long MOVIE_DURATION = 7000;

    public void mixure(String audio_path, int img_length) {
        MOVIE_DURATION = img_length * 1000;
        mCyclicBarrier = new CyclicBarrier(2);

        AudioRecordThread audioRecordThread = new AudioRecordThread(context, audio_path, mediamuxer, mCyclicBarrier, MOVIE_DURATION);
        audioRecordThread.start();

    }

    private static class CodecInputSurface2 {

        private EGLContext EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        private EGLDisplay EGL_NO_DISPLAY = EGL14.EGL_NO_DISPLAY;
        private EGLSurface EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
        private Surface surface;

        public CodecInputSurface2(Surface surface) {
            if (surface == null) {
                throw new NullPointerException();
            }
            this.surface = surface;
            eglSetup();
        }
        
        private void eglCreator(String str) {
            int eglGetError = EGL14.eglGetError();
            if (eglGetError != 12288) {
                throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
            }
        }

        private void eglSetup() {
            this.EGL_NO_DISPLAY = EGL14.eglGetDisplay(0);
            if (this.EGL_NO_DISPLAY == EGL14.EGL_NO_DISPLAY) {
                throw new RuntimeException("unable to get EGL14 display");
            }
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(this.EGL_NO_DISPLAY, iArr, 0, iArr, 1)) {
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                EGL14.eglChooseConfig(this.EGL_NO_DISPLAY, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12610, 1, 12344}, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0);
                eglCreator("eglCreateContext RGB888+recordable ES2");
                this.EGL_NO_CONTEXT = EGL14.eglCreateContext(this.EGL_NO_DISPLAY, eGLConfigArr[0], EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
                eglCreator("eglCreateContext");
                this.EGL_NO_SURFACE = EGL14.eglCreateWindowSurface(this.EGL_NO_DISPLAY, eGLConfigArr[0], this.surface, new int[]{12344}, 0);
                eglCreator("eglCreateWindowSurface");
                return;
            }
            throw new RuntimeException("unable to initialize EGL14");
        }

        public void release() {

            if (this.EGL_NO_DISPLAY != EGL14.EGL_NO_DISPLAY) {
                EGL14.eglMakeCurrent(this.EGL_NO_DISPLAY, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroySurface(this.EGL_NO_DISPLAY, this.EGL_NO_SURFACE);
                EGL14.eglDestroyContext(this.EGL_NO_DISPLAY, this.EGL_NO_CONTEXT);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(this.EGL_NO_DISPLAY);
            }


            this.surface.release();
            this.EGL_NO_DISPLAY = EGL14.EGL_NO_DISPLAY;
            this.EGL_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
            this.EGL_NO_SURFACE = EGL14.EGL_NO_SURFACE;
            this.surface = null;

        }
        
        public void permissionEglPresentation(long j) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                EGLExt.eglPresentationTimeANDROID(this.EGL_NO_DISPLAY, this.EGL_NO_SURFACE, j);
            }
            eglCreator("eglPresentationTimeANDROID");
        }

        public void makeCurrent() 
        {
            EGL14.eglMakeCurrent(this.EGL_NO_DISPLAY, this.EGL_NO_SURFACE, this.EGL_NO_SURFACE, this.EGL_NO_CONTEXT);
            eglCreator("eglMakeCurrent");
        }

        public boolean eglSwapBuffers() {
            boolean eglSwapBuffers = EGL14.eglSwapBuffers(this.EGL_NO_DISPLAY, this.EGL_NO_SURFACE);
            eglCreator("eglSwapBuffers");
            return eglSwapBuffers;
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    public ScreenRecorder2(Context context, File file, int i, int i2, float f, int i3) {
        mRecordThread.start();
        this.context = context;
        this.width = i;
        this.height = i2;
        this.frame_rate = f;




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.bufferInfo = new BufferInfo();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.width, this.height);
        }


        Math.max(width, height);

        this.CONSTANT2 = 200000000;
        CONSTANT2 = width * height > 1000 * 1500 ? 8000000 : 4000000;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            createVideoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
            createVideoFormat.setInteger(MediaFormat.KEY_BIT_RATE, CONSTANT2);
            createVideoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, (int) f);
            createVideoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);

        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.moediaCodec = MediaCodec.createEncoderByType("video/avc");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                createVideoFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 0);
                this.moediaCodec.configure(createVideoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            this.codecInputSurface = new CodecInputSurface2(this.moediaCodec.createInputSurface());
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.moediaCodec.start();
        }


        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mediamuxer = new MediaMuxer(file.getPath(), 0);

            }

            this.Constant_mediamuxer = -1;
            this.check = false;
            this.codecInputSurface.makeCurrent();
            this.record = new Record2(new Record4(Record4.Diff_Textures.TEXTURE_2D));
            this.CONSTANT1 = 0;

        } catch (Throwable th) {
            RuntimeException runtimeException = new RuntimeException("MediaMuxer creation failed", th);
        }


    }
    
    private long returnFrameRate(int i) 
    {
        return (((long) i) * 1000000000) / ((long) ((int) this.frame_rate));
    }

    @SuppressLint("WrongConstant")
    private void drainEncoder(boolean z) {


        if (z) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                try {
                    this.moediaCodec.signalEndOfInputStream();
                } catch (Exception e) {
                    Log.d("aaaaa", "drainEncoder:     " + e);
                }
            }
        }


        ByteBuffer[] outputBuffers = new ByteBuffer[0];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            try {
                outputBuffers = this.moediaCodec.getOutputBuffers();
            } catch (Exception e) {
                Log.d("aaaaa", "drainEncoder:     " + e);
            }

        }

        while (true) {

            int dequeueOutputBuffer = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                if (moediaCodec != null) {
                    dequeueOutputBuffer = this.moediaCodec.dequeueOutputBuffer(this.bufferInfo, 10000);
                }

            }


            if (dequeueOutputBuffer == -1) {
                if (!z) {
                    return;
                }
            } else if (dequeueOutputBuffer == -3) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (moediaCodec != null)
                        outputBuffers = this.moediaCodec.getOutputBuffers();
                }
            } else if (dequeueOutputBuffer == -2) {

                if (this.check) {
                    throw new RuntimeException("format changed twice");
                }

                MediaFormat outputFormat = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    if (moediaCodec != null)
                        outputFormat = this.moediaCodec.getOutputFormat();

                }

                Log.d("EncodeAndMuxTest", "encoder output format changed: " + outputFormat);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    this.Constant_mediamuxer = mediamuxer.addTrack(outputFormat);


                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    mediamuxer.start();

                }
                this.check = true;
            } else if (dequeueOutputBuffer < 0) {
                Log.w("EncodeAndMuxTest", "unexpected result from encoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
            } else {
                ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                if (byteBuffer == null) {
                    throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                }
                if ((this.bufferInfo.flags & 2) != 0) {
                    this.bufferInfo.size = 0;
                }

                if (this.bufferInfo.size != 0) {
                    if (this.check) {
                        byteBuffer.position(this.bufferInfo.offset);
                        byteBuffer.limit(this.bufferInfo.offset + this.bufferInfo.size);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            mediamuxer.writeSampleData(this.Constant_mediamuxer, byteBuffer, this.bufferInfo);
                            
                        }
                    } else {
                        throw new RuntimeException("muxer hasn't started");
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (moediaCodec != null)
                        this.moediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                }

                if ((this.bufferInfo.flags & 4) != 0) {
                    break;
                }

            }


        }
        if (!z) {
            Log.w("EncodeAndMuxTest", "reached end of stream unexpectedly");
        }


    }

    private void permssionCheckMediaCodec() {
        if (this.moediaCodec != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.moediaCodec.stop();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.moediaCodec.release();
            }
            this.moediaCodec = null;

        }


        if (this.codecInputSurface != null) {
            this.codecInputSurface.release();
            this.codecInputSurface = null;
        }


        try {
            if (mediamuxer != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    mediamuxer.stop();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    mediamuxer.release();
                }
                mediamuxer = null;
            }
        } catch (Exception e) {
        }


    }

    public void m11713a(Bitmap bitmap) {
        if (this.Constant_bindtexture == -1) {
            GLES20.glGenTextures(1, new int[1], 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, this.Constant_bindtexture);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
        } else {
            GLES20.glBindTexture(3553, this.Constant_bindtexture);
        }
        bitmap.getByteCount();
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect((bitmap.getWidth() * bitmap.getHeight()) * 4);
        allocateDirect.order(ByteOrder.BIG_ENDIAN);
        bitmap.copyPixelsToBuffer(allocateDirect);
        allocateDirect.position(0);
        GLES20.glTexImage2D(3553, 0, 6408, bitmap.getWidth(), bitmap.getHeight(), 0, 6408, 5121, allocateDirect);
    }

    public boolean m11714a() {
        final Handler handler = new Handler(mRecordThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                drainEncoder(true);

                permssionCheckMediaCodec();
                CONSTANT1 = 0;

            }
        });

        return true;
    }


    public void recordBitmap(final Bitmap bitmap) {
        try {
            drainEncoder(false);
        } catch (Exception e) {
        }


        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f);
        m11713a(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
        try {
            GLES20.glViewport(0, 0, width, height);
            Object obj = new float[]{1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
            record.recorder(Constant_bindtexture, Record3.identity_array);
        } catch (Exception e) {
        }


        long aaaaaa = returnFrameRate(CONSTANT1);
        codecInputSurface.permissionEglPresentation(aaaaaa);
        codecInputSurface.eglSwapBuffers();

        CONSTANT1++;
    }
}
