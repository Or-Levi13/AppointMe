package com.example.appointme.model.User;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    private String id = UUID.randomUUID().toString();
    private String email;
    private String type;
    private String fullName;

    public User(String email,String fullName, String type) {
        this.email = email;
        this.fullName = fullName;
        this.type = type;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullName", fullName);
        result.put("email", email);
        result.put("type", type);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
