package com.anvaishy.easytmedc_receptionist_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.anvaishy.easytmedc_receptionist_app.doctors.Doctor;
import com.anvaishy.easytmedc_receptionist_app.medpass.MedPassViewModel;
import com.anvaishy.easytmedc_receptionist_app.medpass.MedicalPassRequestGlobal;
import com.anvaishy.easytmedc_receptionist_app.sos.RequestSOS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataRepository {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void getDoctorList(OnCompleteListener<QuerySnapshot> listener) {

        db.collection("Doctor")
                .get()
                .addOnCompleteListener(listener);

    }
    public static void getRequestList(OnCompleteListener<QuerySnapshot> listener) {
        db.collection("Medical Pass Requests").orderBy("status")
                .get()
                .addOnCompleteListener(listener);
    }

    public static void getDocumentList(String ID,OnCompleteListener<QuerySnapshot> listener) {

        db.collection("Users").document(ID).collection("Document")
                .get()
                .addOnCompleteListener(listener);

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
    public static void updatePass(MedicalPassRequestGlobal medicalPassRequestGlobal,int status){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> student = new HashMap<>();
        String email = medicalPassRequestGlobal.getUid();
        map.put("name",medicalPassRequestGlobal.getName());
        map.put("phoneNo",medicalPassRequestGlobal.getPhoneNo());
        map.put("uid",medicalPassRequestGlobal.getUid());
        map.put("description",medicalPassRequestGlobal.getDescription());
        map.put("status",status);
        map.put("arrival",medicalPassRequestGlobal.getArrival());
        map.put("depart",medicalPassRequestGlobal.getDepart());
        student.put("description",medicalPassRequestGlobal.getDescription());
        student.put("arrival",medicalPassRequestGlobal.getArrival());
        student.put("depart",medicalPassRequestGlobal.getDepart());
        student.put("status",status);
        db.collection("Medical Pass Requests")
                .document(medicalPassRequestGlobal.getDocID())
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
        db.collection("Users").document(medicalPassRequestGlobal.getUid()).collection("Medical Pass Requests")
                .document(medicalPassRequestGlobal.getDocID())
                .set(student)
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

    public static void respondSOS(RequestSOS sos) {

        Log.d("ss", sos.getSOSID());

        db.collection("Ambulance Requests")
                .document(sos.getSOSID())
                .update("Status", true)
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

    public static void getSOSList(OnCompleteListener<QuerySnapshot> listener) {

        db.collection("Ambulance Requests")
                .get()
                .addOnCompleteListener(listener);

    }

}
