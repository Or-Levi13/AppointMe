package com.example.appointme.model;

import android.app.Activity;

import com.example.appointme.model.User.User;
import com.google.firebase.auth.FirebaseUser;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public interface SuccessListener{
        void onComplete(boolean result);
    }

    public interface AddUserListener {
        void onComplete();
    }

    public interface StringListener {
        void onComplete(String data);
    }

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

    public String getUserId(){
        return fireBase.getId();
    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

    public void addUser(final User user, final AddUserListener listener){
        fireBase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void getUserType(String userId, StringListener listener){
        fireBase.getUserType(userId,listener);
    }


}
