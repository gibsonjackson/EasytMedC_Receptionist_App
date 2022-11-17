package com.anvaishy.easytmedc_receptionist_app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AddDoctorViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<Integer> spec = new MutableLiveData<>(0);
    public MutableLiveData<String> start = new MutableLiveData<>("Start Time");
    public MutableLiveData<String> end = new MutableLiveData<>("End Time");


    private MutableLiveData<ArrayList<String>> specList;

    public void add() {
        Doctor d = new Doctor(name.getValue(), specList.getValue().get(spec.getValue()), start.getValue(), end.getValue(), "");
        DataRepository.addDoctor(d);
    }

    public MutableLiveData<ArrayList<String>> getSpecList() {
        if (specList == null) {
            specList = new MutableLiveData<>();
            specList.setValue(DataRepository.getSpecs());
        }
        return specList;
    }

    public void edit(String id) {
        Doctor d = new Doctor(name.getValue(), specList.getValue().get(spec.getValue()), start.getValue(), end.getValue(), id);
        DataRepository.editDoctor(d);
    }
}