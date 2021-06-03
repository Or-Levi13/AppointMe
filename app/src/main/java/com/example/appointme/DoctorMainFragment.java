package com.example.appointme;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Doctor.DoctorAdapter;
import com.example.appointme.model.Model;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.Patient.PatientAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class DoctorMainFragment extends Fragment {

    View view;
    public RecyclerView doctors_rv;
    PatientAdapter patientAdapter;
    List<Patient> patients = new ArrayList<>();
    ProgressBar pb;
    ImageView logout;
    String dr_id;

    SwipeRefreshLayout swipeRefreshLayout;
    AlertDialog.Builder alertBuilder;

    Map<String, Object> updateDoc;
    Map<String, Object> updatePat;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_main, container, false);

        doctors_rv = view.findViewById(R.id.doctor_main_rv);
        doctors_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        doctors_rv.setLayoutManager(layoutManager);
        patientAdapter = new PatientAdapter();
        doctors_rv.setAdapter(patientAdapter);
        alertBuilder = new AlertDialog.Builder(getActivity());

        pb = view.findViewById(R.id.doctor_main_pb);
        pb.setVisibility(View.VISIBLE);

        logout = view.findViewById(R.id.doctors_main_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilder.setMessage("Are you sure?")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Model.instance.signOutFB();
                                Navigation.findNavController(view).popBackStack();
                            }
                        });
                AlertDialog alert = alertBuilder.create();
                alert.setTitle("Sign Out");
                alert.show();
            }
        });

        dr_id = Model.instance.getUserId();
        Model.instance.showWaitingList(dr_id,new Model.ListListener<Patient>() {
            @Override
            public void onComplete(List<Patient> patientsList) {
                patients = patientsList;
                patientAdapter.setPatientsData(patients);
                pb.setVisibility(View.INVISIBLE);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.doctors_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                updateDoc = new HashMap<>();
                updatePat = new HashMap<>();
                Model.instance.showWaitingList(dr_id,new Model.ListListener<Patient>() {
                    @Override
                    public void onComplete(List<Patient> patientsList) {
                        patients = patientsList;
                        // checking if patient 0 is more than 5 minutes inside
                        if (patients.size()!=0){
                            String first_pat_arrive = patients.get(0).getArrivalTime();
                            Date currDate = new Date();
                            try{
                                Date pat_arrive = formatter.parse(first_pat_arrive);
                                long timeDiff = currDate.getTime() - pat_arrive.getTime();
                                long difference_In_Minutes
                                        = TimeUnit
                                        .MILLISECONDS
                                        .toMinutes(timeDiff)
                                        % 60;
                                long difference_In_Hours
                                        = TimeUnit
                                        .MILLISECONDS
                                        .toHours(timeDiff)
                                        % 24;
                                if (difference_In_Minutes > 5 || difference_In_Hours > 0){
                                    String first_pat_id = patients.get(0).getId();
                                    patients.remove(0);
                                    updatePat.put("ArrivalTime",null);
                                    updateDoc.put("waitingPatients", patients);
                                    if (patients.size() == 0){
                                        updateDoc.put("isAvailable","true");
                                    }
                                    updateDoc.put("lastUpdated", formatter.format(currDate));
                                    Model.instance.updateDoctor(dr_id, updateDoc, new Model.SuccessListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            Log.d(TAG, "Updated Doctor");
                                        }
                                    });
                                    Model.instance.updatePatient(first_pat_id, updatePat, new Model.SuccessListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            Log.d(TAG, "Updated Patient");
                                        }
                                    });
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        patientAdapter.setPatientsData(patients);
                        pb.setVisibility(View.INVISIBLE);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}