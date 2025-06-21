package com.birthday.video.maker.Birthday_Video.videorecord.recorder;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;

public class Record4 {

    private float value_arraycpy;
    private float[] array_cpy = new float[9];
    private int value_attrb_uniform;
    private Diff_Textures diff_texture;
    private float[] array_uniform2f;
    private int bindtexture1;
    private int attrib_val;
    private int texture_coord;
    private int color_adjust_val;
    private int ukernal_val;
    private int umvp_val;
    private int utext_val;
    private int text_off_val;

    public enum Diff_Textures {
        TEXTURE_2D,
        TEXTURE_EXT,
        TEXTURE_EXT_BW,
        TEXTURE_EXT_FILT
    }

    public Record4(Diff_Textures diff_textures) {
        this.diff_texture = diff_textures;
        switch (diff_textures) {
            case TEXTURE_2D:
                this.bindtexture1 = 3553;
                this.value_attrb_uniform = Record3.createShaderProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
                break;
            case TEXTURE_EXT:
                this.bindtexture1 = 36197;
                this.value_attrb_uniform = Record3.createShaderProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
                break;
            case TEXTURE_EXT_BW:
                this.bindtexture1 = 36197;
                this.value_attrb_uniform = Record3.createShaderProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    vec4 tc = texture2D(sTexture, vTextureCoord);\n    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n    gl_FragColor = vec4(color, color, color, 1.0);\n}\n");
                break;
            case TEXTURE_EXT_FILT:
                this.bindtexture1 = 36197;
                this.value_attrb_uniform = Record3.createShaderProgram("uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\n#define KERNEL_SIZE 9\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nuniform float uKernel[KERNEL_SIZE];\nuniform vec2 uTexOffset[KERNEL_SIZE];\nuniform float uColorAdjust;\nvoid main() {\n    int i = 0;\n    vec4 sum = vec4(0.0);\n    if (vTextureCoord.x < vTextureCoord.y - 0.005) {\n        for (i = 0; i < KERNEL_SIZE; i++) {\n            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n            sum += texc * uKernel[i];\n        }\n    sum += uColorAdjust;\n    } else if (vTextureCoord.x > vTextureCoord.y + 0.005) {\n        sum = texture2D(sTexture, vTextureCoord);\n    } else {\n        sum.r = 1.0;\n    }\n    gl_FragColor = sum;\n}\n");
                break;

            default:
                throw new RuntimeException("Unhandled type " + diff_textures);

        }
        if (this.value_attrb_uniform == 0) {
            throw new RuntimeException("Unable to create program");
        }
        Log.d("Grafika", "Created program " + this.value_attrb_uniform + " (" + diff_textures + ")");
        this.attrib_val = GLES20.glGetAttribLocation(this.value_attrb_uniform, "aPosition");
        Record3.excepionCheck(this.attrib_val, "aPosition");
        this.texture_coord = GLES20.glGetAttribLocation(this.value_attrb_uniform, "aTextureCoord");
        Record3.excepionCheck(this.texture_coord, "aTextureCoord");
        this.umvp_val = GLES20.glGetUniformLocation(this.value_attrb_uniform, "uMVPMatrix");
        Record3.excepionCheck(this.umvp_val, "uMVPMatrix");
        this.utext_val = GLES20.glGetUniformLocation(this.value_attrb_uniform, "uTexMatrix");
        Record3.excepionCheck(this.utext_val, "uTexMatrix");
        this.ukernal_val = GLES20.glGetUniformLocation(this.value_attrb_uniform, "uKernel");
        if (this.ukernal_val < 0) {
            this.ukernal_val = -1;
            this.text_off_val = -1;
            this.color_adjust_val = -1;
            return;
        }
        this.text_off_val = GLES20.glGetUniformLocation(this.value_attrb_uniform, "uTexOffset");
        Record3.excepionCheck(this.text_off_val, "uTexOffset");
        this.color_adjust_val = GLES20.glGetUniformLocation(this.value_attrb_uniform, "uColorAdjust");
        Record3.excepionCheck(this.color_adjust_val, "uColorAdjust");
        copyKernalSize(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f}, 0.0f);
        createArayUniform(256, 256);
    }

    public void createArayUniform(int i, int i2) {
        float f = 1.0f / ((float) i);
        float f2 = 1.0f / ((float) i2);
        this.array_uniform2f = new float[]{-f, -f2, 0.0f, -f2, f, -f2, -f, 0.0f, 0.0f, 0.0f, f, 0.0f, -f, f2, 0.0f, f2, f, f2};
    }

    public void copyKernalSize(float[] fArr, float f) {
        if (fArr.length != 9) {
            throw new IllegalArgumentException("Kernel size is " + fArr.length + " vs. " + 9);
        }
        System.arraycopy(fArr, 0, this.array_cpy, 0, 9);
        this.value_arraycpy = f;
    }

    public void setUpGlValues(float[] fArr, FloatBuffer floatBuffer, int i, int i2, int i3, int i4, float[] fArr2, FloatBuffer floatBuffer2, int i5, int i6) {
        Record3.createDiffPrograms("draw start");
        GLES20.glUseProgram(this.value_attrb_uniform);
        Record3.createDiffPrograms("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.bindtexture1, i5);
        GLES20.glUniformMatrix4fv(this.umvp_val, 1, false, fArr, 0);
        Record3.createDiffPrograms("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.utext_val, 1, false, fArr2, 0);
        Record3.createDiffPrograms("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.attrib_val);
        Record3.createDiffPrograms("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.attrib_val, i3, 5126, false, i4, floatBuffer);
        Record3.createDiffPrograms("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.texture_coord);
        Record3.createDiffPrograms("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.texture_coord, 2, 5126, false, i6, floatBuffer2);
        Record3.createDiffPrograms("glVertexAttribPointer");
        if (this.ukernal_val >= 0) {
            GLES20.glUniform1fv(this.ukernal_val, 9, this.array_cpy, 0);
            GLES20.glUniform2fv(this.text_off_val, 9, this.array_uniform2f, 0);
            GLES20.glUniform1f(this.color_adjust_val, this.value_arraycpy);
        }
        GLES20.glDrawArrays(5, i, i2);
        Record3.createDiffPrograms("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.attrib_val);
        GLES20.glDisableVertexAttribArray(this.texture_coord);
        GLES20.glBindTexture(this.bindtexture1, 0);
        GLES20.glUseProgram(0);
    }

}
