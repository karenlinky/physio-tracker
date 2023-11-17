package com.kykarenlin.physiotracker.ui.eventlogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventLogsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventLogsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}