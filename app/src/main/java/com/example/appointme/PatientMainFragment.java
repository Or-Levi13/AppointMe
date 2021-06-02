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
import android.widget.Toast;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Doctor.DoctorAdapter;
import com.example.appointme.model.Model;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.Patient.PatientAdapter;
import com.example.appointme.model.User.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientMainFragment extends Fragment {

    View view;
    public RecyclerView patients_rv;
    DoctorAdapter doctorAdapter;
    List<Doctor> doctors = new ArrayList<>();
    ProgressBar pb;
    ImageView logout;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_patient_main, container, false);

        patients_rv = view.findViewById(R.id.patient_main_rv);
        patients_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        patients_rv.setLayoutManager(layoutManager);
        doctorAdapter = new DoctorAdapter();
        patients_rv.setAdapter(doctorAdapter);

        pb = view.findViewById(R.id.patient_main_pb);
        pb.setVisibility(View.VISIBLE);

        logout = view.findViewById(R.id.patients_main_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.signOutFB();
                Navigation.findNavController(view).popBackStack();
            }
        });

        Model.instance.showAllDoctors(new Model.ListListener<Doctor>() {
            @Override
            public void onComplete(List<Doctor> doctorList) {
                doctors = doctorList;
                doctorAdapter.setDoctorsData(doctors);
                pb.setVisibility(View.INVISIBLE);
            }
        });

        doctorAdapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor, View view) {
                PatientMainFragmentDirections.ActionPatientMainToDoctorDetails action = PatientMainFragmentDirections
                        .actionPatientMainToDoctorDetails(doctor.getFullName(),doctor.getEmail(),doctor.isAvailable(),doctor.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.patients_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Model.instance.showAllDoctors(new Model.ListListener<Doctor>() {
                    @Override
                    public void onComplete(List<Doctor> doctorList) {
                        doctorAdapter.setDoctorsData(doctorList);
                        patients_rv.setAdapter(doctorAdapter);
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}