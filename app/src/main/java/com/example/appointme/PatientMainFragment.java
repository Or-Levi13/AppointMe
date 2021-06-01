package com.example.appointme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class PatientMainFragment extends Fragment {

    View view;
    public RecyclerView patients_rv;
    Button addMeetingBtn;
    DoctorAdapter doctorAdapter;
    List<Doctor> doctors = new ArrayList<>();
    ProgressBar pb;
    ImageView logout;

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



        return view;
    }
}