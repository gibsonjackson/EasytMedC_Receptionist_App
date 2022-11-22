package com.anvaishy.easytmedc_receptionist_app.meddocs;

public class Document {
    String name;
    String link;
    public Document() {}//Left blank for firebase to fill wrapper code
    public Document(String name, String link){
        this.name=name;
        this.link = link;
    }
    public String getName() {
        return name;
    }
    public void setName(String docname) {
        this.name = name;
    }
    public String getLink() {
        return link;
    }
    public void setlink(String docurl) {
        this.link = link;
    }
}