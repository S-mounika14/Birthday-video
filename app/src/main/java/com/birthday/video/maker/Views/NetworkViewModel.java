package com.birthday.video.maker.Views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetworkViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshTriggered = new MutableLiveData<>();
    private boolean shouldRefresh = false;

    public LiveData<Boolean> getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean connected) {
        isConnected.setValue(connected);
    }

    public LiveData<Boolean> getRefreshTriggered() {
        return refreshTriggered;
    }

    public void triggerRefresh() {
        shouldRefresh = true;
        refreshTriggered.setValue(true);
    }

    public boolean shouldRefresh() {
        return shouldRefresh;
    }

    public void setShouldRefresh(boolean shouldRefresh) {
        this.shouldRefresh = shouldRefresh;
    }
    public void resetRefreshTrigger() {
        refreshTriggered.setValue(false);
    }
    public void resetRefresh() {
        refreshTriggered.setValue(false);
    }
}