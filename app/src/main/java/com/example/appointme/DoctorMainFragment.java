package com.example.appointme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.List;

public class DoctorMainFragment extends Fragment {

    View view;
    public RecyclerView doctors_rv;
    PatientAdapter patientAdapter;
    List<Patient> patients = new ArrayList<>();
    ProgressBar pb;
    ImageView logout;
    String dr_id;

    SwipeRefreshLayout swipeRefreshLayout;

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

        pb = view.findViewById(R.id.doctor_main_pb);
        pb.setVisibility(View.VISIBLE);

        logout = view.findViewById(R.id.doctors_main_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.signOutFB();
                Navigation.findNavController(view).popBackStack();
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
                Model.instance.showWaitingList(dr_id,new Model.ListListener<Patient>() {
                    @Override
                    public void onComplete(List<Patient> patientsList) {
                        patients = patientsList;
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