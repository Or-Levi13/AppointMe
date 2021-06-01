package com.example.appointme.model.Patient;

import com.example.appointme.model.User.User;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Patient extends User {

    private String id;
    private String email;
    private String type;
    private String fullName;

    public Patient(){
        super();
    }

    public Patient(String email,String fullName, String type) {
        super(email,fullName,type);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullName", getFullName());
        result.put("email", getEmail());
        result.put("type", getType());
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        id = (String) map.get("id");
        fullName = (String)map.get("fullName");
        email = (String)map.get("email");
        type = (String)map.get("type");
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
}
