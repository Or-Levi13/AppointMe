package com.example.appointme.model.Doctor;

import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.User.User;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Doctor extends User {

    private String id;
    private String email;
    private String type;
    private String fullName;

    List<Patient> patientList = new ArrayList<>();
    boolean isAvailable = false;

    public Doctor(){
        super();
    }

    public Doctor(String email, String fullName, String type) {
        super(email, fullName, type);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getId());
        result.put("fullName", getFullName());
        result.put("email", getEmail());
        result.put("type", getType());
        result.put("lastUpdated", FieldValue.serverTimestamp());
        result.put("isAvailable", isAvailable());
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        id = (String) map.get("id");
        fullName = (String)map.get("fullName");
        email = (String)map.get("email");
        type = (String)map.get("type");
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public boolean isAvailable() {
        return isAvailable;
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
