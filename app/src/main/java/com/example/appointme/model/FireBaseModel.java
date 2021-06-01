package com.example.appointme.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FireBaseModel {

    public FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public String getId(){return mAuth.getCurrentUser().getUid();}

    public void getUserDetails(){
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

    public Boolean isUserExist(){
        if(mAuth.getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void signOutFromFireBase (){
        mAuth.signOut();
    }

    public void signUpToFirebase(User user, String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    user.setId(mAuth.getCurrentUser().getUid());
                    Model.instance.addUser(user,()->{ });
                    Toast.makeText(activity, "User Created Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(activity, "User Failed To Create", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logInToFireBase (String email, String password, Activity activity, Model.SuccessListener listener){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(activity, "Sign-In Success", Toast.LENGTH_SHORT).show();
                    listener.onComplete(true);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(activity, "Sign-In Error", Toast.LENGTH_SHORT).show();
                    listener.onComplete(false);
                }
            }
        });
    }

    public void addUser(User user, final Model.AddUserListener listener) {
        if (user.getType().equals("Doctor")){
            db.collection("Doctors").document(user.getId())
                    .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG","Doctor added successfully");
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG","fail adding Doctor");
                    listener.onComplete();
                }
            });
        }
        if (user.getType().equals("Patient")){
            db.collection("Patients").document(user.getId())
                    .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG","Patient added successfully");
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG","fail adding Patient");
                    listener.onComplete();
                }
            });
        }
    }

    public void getUserType(String userId, Model.StringListener listener) {
        db.collection("Doctors").document(userId).get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String type = doc.getString("type");
                    if (type == null){
                        type = "Patient";
                    }
                    listener.onComplete(type);
                }
            }
        });
    }

    public void showAllDoctors(final Model.ListListener<Doctor> listener){
        List<Doctor> doctorList = new LinkedList<Doctor>();
        db.collection("Doctors").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty() == false) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Doctor doctor = new Doctor();
                            doctor.fromMap(doc.getData());
                            doctorList.add(doctor);
                            //Log.d("TAG","game: " + game.getId());
                        }
                    }
                }
                listener.onComplete(doctorList);
            }
        });
    }

    public void showAllPatients(final Model.ListListener<Patient> listener){
        List<Patient> patientsList = new LinkedList<Patient>();
        db.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty() == false) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            Patient patient = new Patient();
                            patient.fromMap(doc.getData());
                            patientsList.add(patient);
                            //Log.d("TAG","game: " + game.getId());
                        }
                    }
                }
                listener.onComplete(patientsList);
            }
        });
    }

}
