package com.anvaishy.easytmedc_receptionist_app;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SOSViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<ArrayList<RequestSOS>> currentList = new MutableLiveData<>();;

    public void getList() {
        currentList.setValue(DataRepository.getSOSList());
    }

    public void getListToday() {
        currentList.setValue(DataRepository.getSOSListToday());
    }
}