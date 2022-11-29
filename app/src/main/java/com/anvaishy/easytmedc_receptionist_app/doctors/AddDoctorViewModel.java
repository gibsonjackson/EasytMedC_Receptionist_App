package com.anvaishy.easytmedc_receptionist_app.doctors;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

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
        }
        DataRepository.getSpecs(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();

                        list.add((String) doc.get("name"));
                    }
                }

                specList.setValue(list);
            }
        });
        return specList;
    }


    public void edit(String id) {
        Log.e("Some VM data index",spec.getValue()+"---");
        Doctor d = new Doctor(name.getValue(), specList.getValue().get(spec.getValue()), start.getValue(), end.getValue(), id);
        DataRepository.editDoctor(d);
    }
}