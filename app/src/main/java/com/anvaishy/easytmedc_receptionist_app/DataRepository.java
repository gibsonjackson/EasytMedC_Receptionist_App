package com.anvaishy.easytmedc_receptionist_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataRepository {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ArrayList<Doctor> getDoctorList() {
        ArrayList<Doctor> list = new ArrayList<>();

        db.collection("Doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();

                                list.add(
                                        new Doctor(
                                                (String) doc.get("Name"),
                                                (String) doc.get("Specialisation"),
                                                (String) doc.get("Start time"),
                                                (String) doc.get("End time"),
                                                document.getId()
                                        )
                                );
                            }
                        }
                    }
                });

        return list;
    }

    public static ArrayList<String> getSpecs() {
        ArrayList<String> list = new ArrayList<>();

        db.collection("Specialization")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();

                                list.add((String) doc.get("name"));
                            }
                        }
                    }
                });

        return list;
    }

    public static void deleteDoctor(String id) {
        db.collection("Doctor")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
//
    public static void editDoctor(Doctor doctor) {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", doctor.getName());
        map.put("Specialisation", doctor.getSpecialisation());
        map.put("Start time", doctor.getStartTime());
        map.put("End time", doctor.getEndTime());

        db.collection("Doctor")
                .document(doctor.getDocID())
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
//
    public static void addDoctor(Doctor doctor) {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", doctor.getName());
        map.put("Specialisation", doctor.getSpecialisation());
        map.put("Start time", doctor.getStartTime());
        map.put("End time", doctor.getEndTime());

        db.collection("Doctor")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static ArrayList<RequestSOS> getSOSList() {

        ArrayList<RequestSOS> list = new ArrayList<>();

        db.collection("Ambulance Requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("o", "one reqyest pls" + task.getResult().getDocuments().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                Log.d("o", "one reqyest pls");
                                list.add(
                                        new RequestSOS(
                                                (String) doc.get("Name"),
                                                (String) doc.get("UID"),
                                                (String) doc.get("Phone No"),
                                                (String) doc.get("Description"),
                                                ((GeoPoint) Objects.requireNonNull(doc.get("location"))).toString(),
                                                (boolean) doc.get("Status"),
                                                ((Timestamp) doc.get("time")).toDate().getTime(),
                                                document.getId()
                                        )
                                );
                            }
                        }
                    }
                });

        return list;

    }

    public static ArrayList<RequestSOS> getSOSListToday() {

        ArrayList<RequestSOS> list = new ArrayList<>();

        db.collection("Ambulance Requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();


                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    LocalDate date = Instant
                                            .ofEpochMilli(((Timestamp) doc.get("time")).toDate().getTime())
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate();

                                    if(date.getDayOfMonth() == LocalDate.now().getDayOfMonth())

                                        list.add(
                                                new RequestSOS(
                                                        (String) doc.get("Name"),
                                                        (String) doc.get("UID"),
                                                        (String) doc.get("Phone No"),
                                                        (String) doc.get("Description"),
                                                        ((GeoPoint) Objects.requireNonNull(doc.get("location"))).toString(),
                                                        (boolean) doc.get("Status"),
                                                        ((Timestamp) doc.get("time")).toDate().getTime(),
                                                        document.getId()
                                                )
                                        );
                                }
                            }
                        }
                    }
                });

        return list;

    }
//
//    public static void respondSOS(String id) {
//
//    }
}
