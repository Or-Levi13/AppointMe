package com.example.appointme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.Patient.PatientAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientMainFragment extends Fragment {

    View view;
    public RecyclerView patients_rv;
    Button addMeetingBtn;
    PatientAdapter patientAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_patient_main, container, false);

        patients_rv = view.findViewById(R.id.patient_main_rv);
        patients_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        patients_rv.setLayoutManager(layoutManager);
        patientAdapter = new PatientAdapter();
        patients_rv.setAdapter(patientAdapter);

        List<Patient> patients = new ArrayList<>();
        Patient pat = new Patient("dude","dude","dude");
        patients.add(pat);
        patientAdapter.setPatientsData(patients);

        return view;
    }
}