package com.example.appointme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Doctor.DoctorAdapter;
import com.example.appointme.model.Model;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.Patient.PatientAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DoctorDetailsFragment extends Fragment {

    View view;
    TextView dr_name;
    TextView dr_email;
    String dr_id;
    String patient_id;

    public RecyclerView dr_patients_rv;
    Button addMeetingBtn;
    Button cancelBtn;
    PatientAdapter patientAdapter;
    ProgressBar pb;
    ImageView backbtn;

    Map<String, Object> updateDoc;
    List<Patient> patientList = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_details, container, false);
        dr_name = view.findViewById(R.id.dr_details_title);
        dr_email = view.findViewById(R.id.dr_details_email);
        dr_name.setText(DoctorDetailsFragmentArgs.fromBundle(getArguments()).getDrName());
        dr_email.setText(DoctorDetailsFragmentArgs.fromBundle(getArguments()).getDrEmail());
        dr_id = DoctorDetailsFragmentArgs.fromBundle(getArguments()).getDrId();
        patient_id = Model.instance.getUserId();

        dr_patients_rv = view.findViewById(R.id.dr_details_rv);
        dr_patients_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        dr_patients_rv.setLayoutManager(layoutManager);
        patientAdapter = new PatientAdapter();

        Model.instance.showWaitingList(dr_id, new Model.ListListener() {
            @Override
            public void onComplete(List List) {
                patientList = List;
                patientAdapter.setPatientsData(patientList);
                dr_patients_rv.setAdapter(patientAdapter);
                pb.setVisibility(View.INVISIBLE);
            }
        });

        addMeetingBtn = view.findViewById(R.id.dr_details_set_btn);
        addMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoc = new HashMap<>();
                patientList = new ArrayList<>();
                String patientId = Model.instance.getUserId();
                Model.instance.getCurrentPatient(patientId, new Model.patientListener() {
                    @Override
                    public void onComplete(Patient patient) {
                        Model.instance.getCurrentDoctor(dr_id, new Model.doctorListener() {
                            @Override
                            public void onComplete(Doctor doctor) {
                                patientList = doctor.getPatientList();
                                patientList.add(patient);
                                updateDoc.put("isAvailable","false");
                                updateDoc.put("lastUpdated", formatter.format(date));
                                updateDoc.put("waitingPatients", patientList);
                                Model.instance.updateDoctor(doctor.getId(), updateDoc, new Model.SuccessListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        Toast.makeText(getActivity(), "Added to waiting list", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });
        cancelBtn = view.findViewById(R.id.dr_details_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoc = new HashMap<>();
                Model.instance.showWaitingList(dr_id, new Model.ListListener() {
                    @Override
                    public void onComplete(List List) {
                        patientList = List;
                        for (Patient pat : patientList){
                            if (pat.getId().equals(patient_id)){
                                patientList.remove(pat);
                            }
                        }
                        updateDoc.put("waitingPatients",patientList);
                        if (patientList.size()==0){
                            updateDoc.put("isAvailable","true");
                        }
                        Model.instance.updateDoctor(dr_id, updateDoc, new Model.SuccessListener() {
                            @Override
                            public void onComplete(boolean result) {
                                Toast.makeText(getActivity(), "Removed from waiting list", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        pb = view.findViewById(R.id.dr_details_pb);
        pb.setVisibility(View.VISIBLE);

        backbtn = view.findViewById(R.id.dr_details_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.dr_details_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Model.instance.showWaitingList(dr_id, new Model.ListListener() {
                    @Override
                    public void onComplete(List List) {
                        patientList = List;
                        patientAdapter.setPatientsData(patientList);
                        dr_patients_rv.setAdapter(patientAdapter);
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }
}