package com.example.appointme.model;

import android.app.Activity;

import com.example.appointme.model.User.User;
import com.google.firebase.auth.FirebaseUser;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Boolean isUserLoggedIn(){
        return (fireBase.isUserExist());
    }

    public void signUpFB(User user, String password) {
        fireBase.signUpToFireBase(user,password,mActivity);
    }

    public void logInFB(String email,String password, SuccessListener listener) {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

    public void addUser(){

    }

    public interface SuccessListener{
        void onComplete(boolean result);
    }

}
