package com.anvaishy.easytmedc_receptionist_app.doctors;

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

public class DoctorListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Doctor>> currentList;

    public MutableLiveData<ArrayList<Doctor>> getList() {
        if (currentList == null) {
            currentList = new MutableLiveData<>();
        }
        DataRepository.getDoctorList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Doctor> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();

                        list.add(
                                new Doctor(
                                        (String) doc.get("name"),
                                        (String) doc.get("specialisation"),
                                        (String) doc.get("startTime"),
                                        (String) doc.get("endTime"),
                                        document.getId()
                                )
                        );
                    }
                }

                currentList.setValue(list);
            }
        });
        return currentList;
    }

    public void deleteDoctor(Doctor doctor) {
        DataRepository.deleteDoctor(doctor.getDocID());
        getList();
    }
}