package com.anvaishy.easytmedc_receptionist_app.medpass;

import com.google.firebase.Timestamp;

public class MedicalPassRequestGlobal {
    private String name;
    private String phoneNo;
    private String uid;
    private String description;
    private int status;
    private Timestamp arrival;
    private String docID;
    private Timestamp depart;
    MedicalPassRequestGlobal(String name, String phoneNo, String uid, String description, int status, Timestamp arrival, String docID, Timestamp depart){
        this.arrival=arrival;
        this.docID = docID;
        this.depart=depart;
        this.description=description;
        this.name=name;
        this.phoneNo=phoneNo;
        this.status=status;
        this.uid=uid;
    }

    public Timestamp getDepart() {
        return depart;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public void setDepart(Timestamp depart) {
        this.depart = depart;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
