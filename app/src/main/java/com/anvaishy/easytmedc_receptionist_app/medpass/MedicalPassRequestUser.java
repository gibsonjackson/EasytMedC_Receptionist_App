package com.anvaishy.easytmedc_receptionist_app.medpass;

import com.google.firebase.Timestamp;

public class MedicalPassRequestUser {
    private String docID;
    private String description;
    private int status;
    private Timestamp arrival;
    private Timestamp depart;

    public void setDescription(String description) {
        this.description = description;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public void setDepart(Timestamp depart) {
        this.depart = depart;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public Timestamp getDepart() {
        return depart;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
