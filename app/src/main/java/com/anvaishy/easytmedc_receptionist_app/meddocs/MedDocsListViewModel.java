package com.anvaishy.easytmedc_receptionist_app.meddocs;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;
import com.anvaishy.easytmedc_receptionist_app.doctors.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MedDocsListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Document>> currentList;

    public MutableLiveData<ArrayList<Document>> getList(String ID) {
        if (currentList == null) {
            currentList = new MutableLiveData<>();
        }
        DataRepository.getDocumentList(ID,new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Document> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();

                        list.add(
                                new Document(
                                        (String) doc.get("name"),
                                        (String) doc.get("link")
                                )
                        );
                    }
                }
                currentList.setValue(list);

            }
        });
        return currentList;
    }

}