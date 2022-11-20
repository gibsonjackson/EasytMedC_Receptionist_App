package com.anvaishy.easytmedc_receptionist_app.sos;

public class RequestSOS {
    private final String SOSID;
    private String name;
    private String SID;
    private String phone;
    private String desc;
    private String location;
    private boolean responded;
    private long time;

    public RequestSOS(String name, String SID, String phone, String desc, String location, boolean responded, long time, String id) {
        this.name = name;
        this.SID = SID;
        this.phone = phone;
        this.desc = desc;
        this.location = location;
        this.responded = responded;
        this.time = time;
        this.SOSID = id;
    }

    public String getName() {
        return name;
    }

    public String getSID() {
        return SID;
    }

    public String getPhone() {
        return phone;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocation() {
        return location;
    }

    public boolean isResponded() {
        return responded;
    }

    public long getTime() {
        return time;
    }

    public String getSOSID() {
        return SOSID;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }
}
