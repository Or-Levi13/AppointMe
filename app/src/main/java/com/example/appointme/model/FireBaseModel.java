package com.example.appointme.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

    public void signUpToFireBase (User user, String password, Activity activity){
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
        db.collection("Users").document(user.getId())
                .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","User added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding User");
                listener.onComplete();
            }
        });
    }

    public void getUserType(String userId, Model.StringListener listener) {
        db.collection("Users").document(userId).get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String type = doc.getString("type");
                    listener.onComplete(type);
                }
            }
        });
    }

}
