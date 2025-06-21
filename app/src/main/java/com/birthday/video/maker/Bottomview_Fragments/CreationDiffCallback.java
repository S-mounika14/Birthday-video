package com.birthday.video.maker.Bottomview_Fragments;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.birthday.video.maker.activities.Media;

import java.util.ArrayList;

public class CreationDiffCallback extends DiffUtil.Callback {

    private final ArrayList<Media> mOldEmployeeList;
    private final ArrayList<Media> mNewEmployeeList;

    public CreationDiffCallback(ArrayList<Media> oldEmployeeList, ArrayList<Media> newEmployeeList) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldEmployeeList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEmployeeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean val = mOldEmployeeList.get(oldItemPosition).getId() == mNewEmployeeList.get(newItemPosition).getId();
        return mOldEmployeeList.get(oldItemPosition).getId() == mNewEmployeeList.get(
                newItemPosition).getId();

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Media oldEmployee = mOldEmployeeList.get(oldItemPosition);
        final Media newEmployee = mNewEmployeeList.get(newItemPosition);
        return oldEmployee.getName().equals(newEmployee.getName());

    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
