package com.birthday.video.maker.Birthday_Video.videorecord.recorder;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Record3 {
    public static final float[] identity_array = new float[16];

    static {
        Matrix.setIdentityM(identity_array, 0);
    }

    public static int deleteShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        Record3.createDiffPrograms("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e("Grafika", "Could not compile shader " + i + ":");
        Log.e("Grafika", " " + GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public static int createShaderProgram(String str, String str2) {
        int a = Record3.deleteShader(35633, str);
        if (a == 0) {
            return 0;
        }
        int a2 = Record3.deleteShader(35632, str2);
        if (a2 == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        Record3.createDiffPrograms("glCreateProgram");
        if (glCreateProgram == 0) {
            Log.e("Grafika", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        Record3.createDiffPrograms("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        Record3.createDiffPrograms("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);

        if (iArr[0] == 1)
        {
            return glCreateProgram;
        }

        Log.e("Grafika", "Could not link program: ");
        Log.e("Grafika", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    public static FloatBuffer allocateDirBuffer(float[] fArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        return asFloatBuffer;
    }

    public static void createDiffPrograms(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            String str2 = str + ": glError 0x" + Integer.toHexString(glGetError);
            Log.e("Grafika", str2);
            throw new RuntimeException(str2);
        }
    }

    public static void excepionCheck(int i, String str) {
        if (i < 0) {
            throw new RuntimeException("Unable to locate '" + str + "' in program");
        }
    }
}
