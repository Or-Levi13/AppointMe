package com.example.appointme.model.Patient;

import com.example.appointme.model.User.User;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Patient extends User {

    private String id;
    private String email;
    private String type;
    private String fullName;
    private boolean isWaiting;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    private String arrivalTime;

    public Patient(){
        super();
    }

    public Patient(String email,String fullName, String type) {
        super(email,fullName,type);
        this.email = email;
        this.type = type;
        this.fullName = fullName;
        isWaiting = true;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullName", getFullName());
        result.put("email", getEmail());
        result.put("type", getType());
        result.put("lastUpdated", formatter.format(date));
        result.put("ArrivalTime",getArrivalTime());
        result.put("isWaiting", isWaiting());
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        id = (String) map.get("id");
        fullName = (String)map.get("fullName");
        email = (String)map.get("email");
        type = (String)map.get("type");
        arrivalTime = (String)map.get("ArrivalTime");
        isWaiting = (boolean) map.get("isWaiting");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
