package com.anvaishy.easytmedc_receptionist_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoctorListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Doctor>> currentList;
    private MutableLiveData<ArrayList<String>> specList;

    public MutableLiveData<ArrayList<Doctor>> getList() {
        if (currentList == null) {
            currentList = new MutableLiveData<>();
            currentList.setValue(DataRepository.getDoctorList());
        }
        return currentList;
    }

    public MutableLiveData<ArrayList<String>> getSpecList() {
        if (specList == null) {
            specList = new MutableLiveData<>();
            specList.setValue(DataRepository.getSpecs());
        }
        return specList;
    }

    public void deleteDoctor(Doctor doctor) {
        DataRepository.deleteDoctor(doctor.getDocID());
    }
}