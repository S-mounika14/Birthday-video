package com.birthday.video.maker.Birthday_Video.videorecord.recorder;


public class Record2 {
    private Record4 record4;
    private final Record1 record1 = new Record1(Record1.SHAPES.FULL_RECTANGLE);

    public Record2(Record4 record4) {
        this.record4 = record4;
    }

    public void recorder(int i, float[] fArr) {
        this.record4.setUpGlValues(Record3.identity_array, this.record1.getFloatBufferAllocated(), 0, this.record1.getBufferVal2(), this.record1.getBufferVal(), this.record1.getBufferValue1(), fArr, this.record1.getBufferFloat(), i, this.record1.getValue1());
    }
}
