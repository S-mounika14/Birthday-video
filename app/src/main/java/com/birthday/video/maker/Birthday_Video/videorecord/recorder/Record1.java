package com.birthday.video.maker.Birthday_Video.videorecord.recorder;

import java.nio.FloatBuffer;

public class Record1 {

    private static final float[] array_full_rectance = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    private static final FloatBuffer allocateDirBuffer = Record3.allocateDirBuffer(array_full_rectance);

    private static final float[] array_buffer = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

    private static final FloatBuffer array_buffer_allocated = Record3.allocateDirBuffer(array_buffer);
    private static final float[] array_buffer1 = new float[]{-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f};
    private static final FloatBuffer allocated_array_buffer1 = Record3.allocateDirBuffer(array_buffer1);
    
    private static final float[] array_buffer2 = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final FloatBuffer allocated_array_buffer2 = Record3.allocateDirBuffer(array_buffer2);
    private static final float[] array_buffer3 = new float[]{0.0f, 0.57735026f, -0.5f, -0.28867513f, 0.5f, -0.28867513f};
    private static final FloatBuffer allocated_array_buffer3 = Record3.allocateDirBuffer(array_buffer3);
    private static final float[] array_buffer4 = new float[]{0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static final FloatBuffer allocate_array_buffer4 = Record3.allocateDirBuffer(array_buffer4);
    private int buffer_val;
    private SHAPES shapes;
    private FloatBuffer buffer_float;
    private int value1;
    private FloatBuffer floatBufferAllocated;
    private int buffer_val2;
    private int buffer_val1;

    public enum SHAPES {
        TRIANGLE,
        RECTANGLE,
        FULL_RECTANGLE
    }

    public Record1(SHAPES shapes)
    {

        switch (shapes)
        {
            case TRIANGLE:
                this.floatBufferAllocated = allocated_array_buffer3;
                this.buffer_float = allocate_array_buffer4;
                this.buffer_val = 2;
                this.buffer_val1 = this.buffer_val * 4;
                this.buffer_val2 = array_buffer3.length / this.buffer_val;
                break;
            case RECTANGLE:
                this.floatBufferAllocated = allocated_array_buffer1;
                this.buffer_float = allocated_array_buffer2;
                this.buffer_val = 2;
                this.buffer_val1 = this.buffer_val * 4;
                this.buffer_val2 = array_buffer1.length / this.buffer_val;
                break;
            case FULL_RECTANGLE:
                this.floatBufferAllocated = allocateDirBuffer;
                this.buffer_float = array_buffer_allocated;
                this.buffer_val = 2;
                this.buffer_val1 = this.buffer_val * 4;
                this.buffer_val2 = array_full_rectance.length / this.buffer_val;
                break;
            default:
                throw new RuntimeException("Unknown shape " + shapes);
        }

        this.value1 = 8;
        this.shapes = shapes;

    }


    public FloatBuffer getFloatBufferAllocated() {
        return this.floatBufferAllocated;
    }


    public FloatBuffer getBufferFloat() {
        return this.buffer_float;
    }


    public int getBufferVal2() {
        return this.buffer_val2;
    }


    public int getBufferValue1() {
        return this.buffer_val1;
    }

    public int getValue1() {
        return this.value1;
    }

    public int getBufferVal() {
        return this.buffer_val;
    }

    public String toString() {
        return this.shapes != null ? "[Drawable2d: " + this.shapes + "]" : "[Drawable2d: ...]";
    }
}
