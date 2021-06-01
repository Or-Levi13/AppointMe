package com.example.appointme.model;

import android.app.Activity;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.User.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Map;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public interface SuccessListener{
        void onComplete(boolean result);
    }

    public interface onCompleteListener {
        void onComplete();
    }

    public interface userListener {
        void onComplete(User user);
    }

    public interface patientListener {
        void onComplete(Patient patient);
    }

    public interface doctorListener {
        void onComplete(Doctor doctor);
    }

    public interface StringListener {
        void onComplete(String data);
    }

    public interface ListListener<T> {
        void onComplete(List<T> List);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Boolean isUserLoggedIn(){
        return (fireBase.isUserExist());
    }

    public void signUpFB(User user, String password) {
        fireBase.signUpToFirebase(user,password,mActivity);
    }

    public void logInFB(String email,String password, SuccessListener listener) {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }

    public String getUserId(){
        return fireBase.getId();
    }

    public void getCurrentPatient(String userId, patientListener listener){
        fireBase.getCurrentPatient(userId,listener);
    }

    public void getCurrentDoctor(String userId, doctorListener listener){
        fireBase.getCurrentDoctor(userId,listener);
    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

    public void addUser(final User user, final onCompleteListener listener){
        fireBase.addUser(user, new onCompleteListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void getUserType(String userId, StringListener listener){
        fireBase.getUserType(userId,listener);
    }

    public void showAllDoctors(ListListener<Doctor> listener){
        fireBase.showAllDoctors(listener);
    }

    public void showAllPatients(ListListener<Patient> listener){
        fireBase.showAllPatients(listener);
    }

    public void updateDoctor(String doctorId, Map<String,Object> map, Model.SuccessListener listener){
        fireBase.updateDoctor(doctorId,map,listener);
    }


}
