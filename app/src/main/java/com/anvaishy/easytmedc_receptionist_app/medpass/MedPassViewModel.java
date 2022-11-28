package com.anvaishy.easytmedc_receptionist_app.medpass;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MedPassViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<MedicalPassRequestGlobal>> currentList;
    public MutableLiveData<ArrayList<MedicalPassRequestGlobal>> getList() {
        if (currentList == null) {
            currentList = new MutableLiveData<>();
        }
        DataRepository.getRequestList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<MedicalPassRequestGlobal> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> doc = document.getData();
                        list.add(new MedicalPassRequestGlobal(

                                (String)        doc.get("name"),
                                (String) doc.get("phoneNo"),
                                (String)   doc.get("uid"),
                                (String) doc.get("description"),
                                Math.toIntExact((Long) doc.get("status")),
                                (Timestamp) doc.get("arrival"),
                                document.getId(),
                                (Timestamp)  doc.get("depart")
                        ));
                    }
                }
                currentList.setValue(list);
            }
        });
        return currentList;
    }
}