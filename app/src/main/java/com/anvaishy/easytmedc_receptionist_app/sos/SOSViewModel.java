package com.anvaishy.easytmedc_receptionist_app.sos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anvaishy.easytmedc_receptionist_app.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class SOSViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<RequestSOS>> currentList;
    public MutableLiveData<Boolean> isToday = new MutableLiveData<>(true);


    public MutableLiveData<ArrayList<RequestSOS>> getList() {
        if (currentList == null) {
            currentList = new MutableLiveData<>();
        }
        if(isToday.getValue()) DataRepository.getSOSList(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<RequestSOS> list = new ArrayList<>();

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDate date = Instant
                                    .ofEpochMilli(((Timestamp) doc.get("time")).toDate().getTime())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

                            if (date.getDayOfMonth() == LocalDate.now().getDayOfMonth())
                                list.add(
                                        new RequestSOS(
                                                (String) doc.get("Name"),
                                                (String) doc.get("UID"),
                                                (String) doc.get("Phone No"),
                                                (String) doc.get("Description"),
                                                (GeoPoint) Objects.requireNonNull(doc.get("location")),
                                                (boolean) doc.get("Status"),
                                                ((Timestamp) doc.get("time")).toDate().getTime(),
                                                document.getId()
                                        )
                                );
                        }
                    }

                    currentList.setValue(list);
                }
            }
        });

        else DataRepository.getSOSList(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<RequestSOS> list = new ArrayList<>();

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();
                        list.add(
                                new RequestSOS(
                                        (String) doc.get("Name"),
                                        (String) doc.get("UID"),
                                        (String) doc.get("Phone No"),
                                        (String) doc.get("Description"),
                                        (GeoPoint) Objects.requireNonNull(doc.get("location")),
                                        (Boolean) doc.get("Status"),
                                        ((Timestamp) doc.get("time")).toDate().getTime(),
                                        document.getId()
                                )
                        );
                    }

                    currentList.setValue(list);
                }
            }
        });

        return currentList;
    }

    public void respondSOS(RequestSOS sos) {
        DataRepository.respondSOS(sos);
        getList();
    }

}