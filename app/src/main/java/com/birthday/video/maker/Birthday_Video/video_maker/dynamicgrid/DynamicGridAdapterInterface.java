package com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid;

public interface DynamicGridAdapterInterface {
    boolean canReorder(int i);

    int getColumnCount();

    void reorderItems(int i, int i2);
}
