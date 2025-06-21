package com.birthday.video.maker.Birthday_Video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaCodec;
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
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import com.birthday.video.maker.Birthday_Video.videorecord.recorder.Record2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CyclicBarrier;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class GLMovieRecorder
{

    private static final String TAG = "GLMovieRecorder";
    private Context mContext;
    private boolean mInited;
    private float f8419a = 30.0f;
    private int f8420b = 0;
    private HandlerThread mRecordThread = new HandlerThread("GLMovieRecorder");
    private CyclicBarrier mCyclicBarrier;
    private Exception mAudioRecordException;
    private int f8429k = -1 ;
    private Record2 f8424f;

    public GLMovieRecorder(Context context)
    {
        mContext = context.getApplicationContext();
        mRecordThread.start();
    }

    public void configOutput(int width, int height, int bitRate, int frameRate, int iFrameInterval, String outputPath) {
        mWidth = width;
        mHeight = height;
        mBitRate = bitRate;
        mFrameRate = frameRate;
        mIFrameInterval = iFrameInterval;
        mOutputPath = outputPath;
        mInited = true;
    }


    private static final boolean VERBOSE = true;

    // parameters for the encoder
    private static final String MIME_TYPE = "video/avc";

    // size of a frame, in pixels
    private int mWidth = -1;
    private int mHeight = -1;
    // bit rate, in bits per second
    private int mBitRate = -1;

    private int mFrameRate = 30; //帧率
    private int mIFrameInterval = 10; // 10 seconds between I-frames

    // encoder / muxer state
    private MediaCodec mEncoder;
    private CodecInputSurface2 mInputSurface;
    private MediaMuxer mMuxer;
    private int mTrackIndex;
    private boolean mMuxerStarted;

    // allocate one of these up front so we don't need to do it every time
    private MediaCodec.BufferInfo mBufferInfo;
    //视频输出路径
    private String mOutputPath;

    private int getEven(int n) {
        return n % 2 == 0 ? n : n + 1;
    }

    /**
     * Configures encoder and muxer state, and prepares the input Surface.
     */
    private void prepareEncoder() throws IOException
    {

        mBufferInfo = new MediaCodec.BufferInfo();
        mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        Log.i(TAG, "encoder name:" + mEncoder.getName());

        if (mEncoder.getName().equals("OMX.MTK.VIDEO.ENCODER.AVC")) {
            if (mWidth > mHeight && mWidth > 1920) {
                mHeight = (int) (mHeight / (mWidth / 1920f));
                mWidth = 1920;
                Log.e(TAG, "The encoder limited max size,set size to " + mWidth + " X " + mHeight);
            } else if (mHeight > mWidth && mHeight > 1920) {
                mWidth = (int) (mWidth / (mHeight / 1920f));
                mHeight = 1920;
                Log.e(TAG, "The encoder limited max size,set size to " + mWidth + " X " + mHeight);
            }
        }

        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, getEven(mWidth), getEven(mHeight));
        // Set some properties.  Failing to specify some of these can cause the MediaCodec
        // configure() call to throw an unhelpful exception.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, mFrameRate);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, mIFrameInterval);
        if (VERBOSE) {
            Log.d(TAG, "format: " + format);
        }

        // Create a MediaCodec encoder, and configure it with our format.  Get a Surface
        // we can use for input and wrap it with a class that handles the EGL work.
        //
        // If you want to have two EGL contexts -- one for display, one for recording --
        // you will likely want to defer instantiation of CodecInputSurface until after the
        // "display" EGL context is created, then modify the eglCreateContext call to
        // take eglGetCurrentContext() as the share_context argument.

        mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mInputSurface = new CodecInputSurface2(mEncoder.createInputSurface());
        mEncoder.start();

        // Output filename.  Ideally this would use Context.getFilesDir() rather than a
        // hard-coded output directory.
        String outputPath = mOutputPath;
        Log.d(TAG, "output file is " + outputPath);


        // Create a MediaMuxer.  We can't add the video track and start() the muxer here,
        // because our MediaFormat doesn't have the Magic Goodies.  These can only be
        // obtained from the encoder after it has started processing data.
        //
        // We're not actually interested in multiplexing audio.  We just want to convert
        // the raw H.264 elementary stream we get from MediaCodec into a .mp4 file.
        try {
            mMuxer = new MediaMuxer(outputPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException ioe) {
            throw new RuntimeException("MediaMuxer creation failed", ioe);
        }

        mTrackIndex = -1;
        mMuxerStarted = false;
    }

    /**
     * Releases encoder resources.  May be called after partial / failed initialization.
     */
    private void releaseEncoder() {
        if (VERBOSE) {
            Log.d(TAG, "releasing encoder objects");
        }
        if (mEncoder != null) {
            mEncoder.stop();
            mEncoder.release();
            mEncoder = null;
        }
        if (mInputSurface != null) {
            mInputSurface.release();
            mInputSurface = null;
        }
        if (mMuxer != null) {
//            try {
//                mCyclicBarrier.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (BrokenBarrierException e) {
//                e.printStackTrace();
//            }
            mMuxer.stop();
            mMuxer.release();
            mMuxer = null;
        }
    }

    private void drainEncoder(boolean endOfStream) {
        if (Build.VERSION.SDK_INT < 21) {
            drainEncoderImpl(endOfStream);
        } else {
            drainEncoderApi21(endOfStream);
        }

    }

    /**
     * Extracts all pending data from the encoder.
     * <p/>
     * If endOfStream is not set, this returns when there is no more data to drain.  If it
     * is set, we send EOS to the encoder, and then iterate until we see EOS on the output.
     * Calling this with endOfStream set should be done once, right before stopping the muxer.
     */

    private void drainEncoderImpl(boolean endOfStream) {
        final int TIMEOUT_USEC = 10000;
        if (VERBOSE) {
            Log.d(TAG, "drainEncoder(" + endOfStream + ")");
        }

        if (endOfStream) {
            if (VERBOSE) {
                Log.d(TAG, "sending EOS to encoder");
            }
            mEncoder.signalEndOfInputStream();
        }

        ByteBuffer[] encoderOutputBuffers = mEncoder.getOutputBuffers();
        while (true) {
            int encoderStatus = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_USEC);
            if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                // no output available yet
                if (!endOfStream) {
                    break;      // out of while
                } else {
                    if (VERBOSE) {
                        Log.d(TAG, "no output available, spinning to await EOS");
                    }
                }
            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                // not expected for an encoder
                encoderOutputBuffers = mEncoder.getOutputBuffers();
            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                // should happen before receiving buffers, and should only happen once
                if (mMuxerStarted) {
                    throw new RuntimeException("format changed twice");
                }
                MediaFormat newFormat = mEncoder.getOutputFormat();
                Log.d(TAG, "encoder output format changed: " + newFormat);

                // now that we have the Magic Goodies, start the muxer
                mTrackIndex = mMuxer.addTrack(newFormat);
//                try {
//                    mCyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
                mMuxer.start();
//                try {
//                    mCyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
                mMuxerStarted = true;
            } else if (encoderStatus < 0) {
                Log.w(TAG, "unexpected result from encoder.dequeueOutputBuffer: " +
                        encoderStatus);
                // let's ignore it
            } else {
                ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                if (encodedData == null) {
                    throw new RuntimeException("encoderOutputBuffer " + encoderStatus + " was null");
                }

                if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    // The codec config data was pulled out and fed to the muxer when we got
                    // the INFO_OUTPUT_FORMAT_CHANGED status.  Ignore it.
                    if (VERBOSE) {
                        Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
                    }
                    mBufferInfo.size = 0;
                }

                if (mBufferInfo.size != 0) {
                    if (!mMuxerStarted) {
                        throw new RuntimeException("muxer hasn't started");
                    }

                    // adjust the ByteBuffer values to match BufferInfo (not needed?)
                    encodedData.position(mBufferInfo.offset);
                    encodedData.limit(mBufferInfo.offset + mBufferInfo.size);

                    mMuxer.writeSampleData(mTrackIndex, encodedData, mBufferInfo);
                    if (VERBOSE) {
                        Log.d(TAG, "sent " + mBufferInfo.size + " bytes to muxer");
                    }
                }

                mEncoder.releaseOutputBuffer(encoderStatus, false);

                if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    if (!endOfStream) {
                        Log.w(TAG, "reached end of stream unexpectedly");
                    } else {
                        if (VERBOSE) {
                            Log.d(TAG, "end of stream reached");
                        }
                    }
                    break;      // out of while
                }
            }
        }
    }


    /**
     * Extracts all pending data from the encoder.
     * <p/>
     * If endOfStream is not set, this returns when there is no more data to drain.  If it
     * is set, we send EOS to the encoder, and then iterate until we see EOS on the output.
     * Calling this with endOfStream set should be done once, right before stopping the muxer.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drainEncoderApi21(boolean endOfStream) {
        final int TIMEOUT_USEC = 10000;
        if (VERBOSE) {
            Log.d(TAG, "drainEncoder(" + endOfStream + ")");
        }

        if (endOfStream)
        {

            if (VERBOSE) {
                Log.d(TAG, "sending EOS to encoder");
            }

            mEncoder.signalEndOfInputStream();

        }


        while (true)
        {

            int encoderIndex = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_USEC);
            if (encoderIndex == MediaCodec.INFO_TRY_AGAIN_LATER)
            {
                // no output available :)

                if (!endOfStream)
                {
                    break;      // out of while
                }
                else
                {
                    if (VERBOSE) {
                        Log.d(TAG, "no output available, spinning to await EOS");
                    }
                }

            } else if (encoderIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                // not expected for an encoder
            } else if (encoderIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                // should happen before receiving buffers, and should only happen once
                if (mMuxerStarted) {
                    throw new RuntimeException("format changed twice");
                }
                MediaFormat newFormat = mEncoder.getOutputFormat();
                Log.d(TAG, "encoder output format changed: " + newFormat);

                // now that we have the Magic Goodies, start the muxer
                mTrackIndex = mMuxer.addTrack(newFormat);
//                try {
//                    mCyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
                mMuxer.start();
//                try {
//                    mCyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
                mMuxerStarted = true;
            } else if (encoderIndex < 0) {
                Log.w(TAG, "unexpected result from encoder.dequeueOutputBuffer: " +
                        encoderIndex);
                // let's ignore it
            } else {
                ByteBuffer outputBuffer = mEncoder.getOutputBuffer(encoderIndex);
                if (outputBuffer == null) {
                    throw new RuntimeException("encoderOutputBuffer " + encoderIndex + " was null");
                }

                if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    // The codec config data was pulled out and fed to the muxer when we got
                    // the INFO_OUTPUT_FORMAT_CHANGED status.  Ignore it.
                    if (VERBOSE) {
                        Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
                    }
                    mBufferInfo.size = 0;
                }

                if (mBufferInfo.size != 0) {
                    if (!mMuxerStarted) {
                        throw new RuntimeException("muxer hasn't started");
                    }

                    // adjust the ByteBuffer values to match BufferInfo (not needed?)
                    outputBuffer.position(mBufferInfo.offset);
                    outputBuffer.limit(mBufferInfo.offset + mBufferInfo.size);

                    mMuxer.writeSampleData(mTrackIndex, outputBuffer, mBufferInfo);
                    if (VERBOSE) {
                        Log.d(TAG, "sent " + mBufferInfo.size + " bytes to muxer");
                    }
                }

                mEncoder.releaseOutputBuffer(encoderIndex, false);

                if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    if (!endOfStream) {
                        Log.w(TAG, "reached end of stream unexpectedly");
                    } else {
                        if (VERBOSE) {
                            Log.d(TAG, "end of stream reached");
                        }
                    }
                    break;      // out of while
                }
            }
        }
    }

    /**
     * Generates the presentation time for frame N, in nanoseconds.
     */
    private long computePresentationTimeNsec(int frameIndex) {
        final long ONE_BILLION = 1000000000;
        return frameIndex * ONE_BILLION / mFrameRate;
    }

    /**
     * Holds state associated with a Surface used for MediaCodec encoder input.
     * <p/>
     * The constructor takes a Surface obtained from MediaCodec.createInputSurface(), and uses that
     * to create an EGL window surface.  Calls to eglSwapBuffers() cause a frame of data to be sent
     * to the video encoder.
     * <p/>
     * This object owns the Surface -- releasing this will release the Surface too.
     */
    private static class CodecInputSurface {
        private static final int EGL_RECORDABLE_ANDROID = 0x3142;

        private EGLDisplay mEGLDisplay = EGL14.EGL_NO_DISPLAY;
        private EGLContext mEGLContext = EGL14.EGL_NO_CONTEXT;
        private EGLSurface mEGLSurface = EGL14.EGL_NO_SURFACE;

        private Surface mSurface;

        /**
         * Creates a CodecInputSurface from a Surface.
         */
        public CodecInputSurface(Surface surface) {
            if (surface == null) {
                throw new NullPointerException();
            }
            mSurface = surface;

            eglSetup();
        }

        /**
         * Prepares EGL.  We want a GLES 2.0 context and a surface that supports recording.
         */
        private void eglSetup() {
            mEGLDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
            if (mEGLDisplay == EGL14.EGL_NO_DISPLAY) {
                throw new RuntimeException("unable to get EGL14 display");
            }
            int[] version = new int[2];
            if (!EGL14.eglInitialize(mEGLDisplay, version, 0, version, 1)) {
                throw new RuntimeException("unable to initialize EGL14");
            }

            // Configure EGL for recording and OpenGL ES 2.0.
            int[] attribList = {
                    EGL14.EGL_RED_SIZE, 8,
                    EGL14.EGL_GREEN_SIZE, 8,
                    EGL14.EGL_BLUE_SIZE, 8,
                    EGL14.EGL_ALPHA_SIZE, 8,
                    EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                    EGL_RECORDABLE_ANDROID, 1,
                    EGL14.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfigs = new int[1];
            EGL14.eglChooseConfig(mEGLDisplay, attribList, 0, configs, 0, configs.length,
                    numConfigs, 0);
            checkEglError("eglCreateContext RGB888+recordable ES2");

            // Configure context for OpenGL ES 2.0.
            int[] attrib_list = {
                    EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                    EGL14.EGL_NONE
            };
            mEGLContext = EGL14.eglCreateContext(mEGLDisplay, configs[0], EGL14.EGL_NO_CONTEXT,
                    attrib_list, 0);
            checkEglError("eglCreateContext");

            // Create a window surface, and attach it to the Surface we received.
            int[] surfaceAttribs = {
                    EGL14.EGL_NONE
            };
            mEGLSurface = EGL14.eglCreateWindowSurface(mEGLDisplay, configs[0], mSurface,
                    surfaceAttribs, 0);
            checkEglError("eglCreateWindowSurface");
        }

        /**
         * Discards all resources held by this class, notably the EGL context.  Also releases the
         * Surface that was passed to our constructor.
         */
        public void release() {
            if (mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
                EGL14.eglMakeCurrent(mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE,
                        EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroySurface(mEGLDisplay, mEGLSurface);
                EGL14.eglDestroyContext(mEGLDisplay, mEGLContext);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(mEGLDisplay);
            }

            mSurface.release();

            mEGLDisplay = EGL14.EGL_NO_DISPLAY;
            mEGLContext = EGL14.EGL_NO_CONTEXT;
            mEGLSurface = EGL14.EGL_NO_SURFACE;

            mSurface = null;
        }

        /**
         * Makes our EGL context and surface current.
         */
        public void makeCurrent() {
            EGL14.eglMakeCurrent(mEGLDisplay, mEGLSurface, mEGLSurface, mEGLContext);
            checkEglError("eglMakeCurrent");
        }

        /**
         * Calls eglSwapBuffers.  Use this to "publish" the current frame.
         */
        public boolean swapBuffers() {
            boolean result = EGL14.eglSwapBuffers(mEGLDisplay, mEGLSurface);
            checkEglError("eglSwapBuffers");
            return result;
        }

        /**
         * Sends the presentation time stamp to EGL.  Time is expressed in nanoseconds.
         */
        public void setPresentationTime(long nsecs) {
            EGLExt.eglPresentationTimeANDROID(mEGLDisplay, mEGLSurface, nsecs);
            checkEglError("eglPresentationTimeANDROID");
        }

        /**
         * Checks for EGL errors.  Throws an exception if one is found.
         */
        private void checkEglError(String msg) {
            int error;
            if ((error = EGL14.eglGetError()) != EGL14.EGL_SUCCESS) {
                throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
            }
        }

    }

    public interface OnRecordListener
    {
        void onRecordFinish(boolean success);

        void onRecordProgress(int recordedDuration, int totalDuration);
    }


    private static class CodecInputSurface2 {

        /* renamed from: a */
        private EGLContext f8415a = EGL14.EGL_NO_CONTEXT;
        /* renamed from: b */
        private EGLDisplay f8416b = EGL14.EGL_NO_DISPLAY;
        /* renamed from: c */
        private EGLSurface f8417c = EGL14.EGL_NO_SURFACE;
        /* renamed from: d */
        private Surface f8418d;

        public CodecInputSurface2(Surface surface) {
            if (surface == null) {
                throw new NullPointerException();
            }
            this.f8418d = surface;
            eglSetup();
        }

        /* renamed from: a */
        private void m11704a(String str) {
            int eglGetError = EGL14.eglGetError();
            if (eglGetError != 12288) {
                throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
            }
        }

        /* renamed from: d */
        private void eglSetup() {
            this.f8416b = EGL14.eglGetDisplay(0);
            
            if (this.f8416b == EGL14.EGL_NO_DISPLAY)
            {
                throw new RuntimeException("unable to get EGL14 display");
            }
            
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(this.f8416b, iArr, 0, iArr, 1)) {
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                EGL14.eglChooseConfig(this.f8416b, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12610, 1, 12344}, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0);
                m11704a("eglCreateContext RGB888+recordable ES2");
                this.f8415a = EGL14.eglCreateContext(this.f8416b, eGLConfigArr[0], EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
                m11704a("eglCreateContext");
                this.f8417c = EGL14.eglCreateWindowSurface(this.f8416b, eGLConfigArr[0], this.f8418d, new int[]{12344}, 0);
                m11704a("eglCreateWindowSurface");
                return;
            }
            throw new RuntimeException("unable to initialize EGL14");
        }

        /* renamed from: a */
        public void release()
        {

            if (this.f8416b != EGL14.EGL_NO_DISPLAY)
            {
                EGL14.eglMakeCurrent(this.f8416b, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroySurface(this.f8416b, this.f8417c);
                EGL14.eglDestroyContext(this.f8416b, this.f8415a);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(this.f8416b);
            }


            this.f8418d.release();
            this.f8416b = EGL14.EGL_NO_DISPLAY;
            this.f8415a = EGL14.EGL_NO_CONTEXT;
            this.f8417c = EGL14.EGL_NO_SURFACE;
            this.f8418d = null;

        }

        /* renamed from: a */
        public void m11707a(long j) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                EGLExt.eglPresentationTimeANDROID(this.f8416b, this.f8417c, j);
            }
            m11704a("eglPresentationTimeANDROID");
        }

        /* renamed from: b */
        public void makeCurrent() {
            EGL14.eglMakeCurrent(this.f8416b, this.f8417c, this.f8417c, this.f8415a);
            m11704a("eglMakeCurrent");
        }

        /* renamed from: c */
        public boolean m11709c() {
            boolean eglSwapBuffers = EGL14.eglSwapBuffers(this.f8416b, this.f8417c);
            m11704a("eglSwapBuffers");
            return eglSwapBuffers;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)



    public void m11713a(Bitmap bitmap)
    {

        if (this.f8429k == -1)
        {
            GLES20.glGenTextures(1, new int[1], 0);
            GLES20.glBindTexture(3553, this.f8429k);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
        } else {
            GLES20.glBindTexture(3553, this.f8429k);
        }

        bitmap.getByteCount();
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect((bitmap.getWidth() * bitmap.getHeight()) * 4);
        allocateDirect.order(ByteOrder.BIG_ENDIAN);
        bitmap.copyPixelsToBuffer(allocateDirect);
        allocateDirect.position(0);
        GLES20.glTexImage2D(3553, 0, 6408, 100, 100, 0, 6408, 5121, allocateDirect);



    }



}
