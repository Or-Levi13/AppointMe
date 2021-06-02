package com.example.appointme.model.Patient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointme.R;

import java.util.LinkedList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    public static List<Patient> patientsData = new LinkedList<Patient>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Patient getPatients(int position)
    {
        return patientsData.get(position);
    }

    public void setPatientsData(List<Patient> patients) {
        this.patientsData = patients;
        notifyDataSetChanged();
    }

    // Create PatientHolder for the adapter.
    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_row, parent, false);
        PatientHolder holder = new PatientHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        Patient currentPatient = patientsData.get(position);
        holder.bindData(currentPatient,position);
        holder.itemView.setTag(currentPatient);
    }

    @Override
    public int getItemCount() {
        return patientsData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Patient patient, View view);
    }

    //---------------PatientHolder----------------//

    public static class PatientHolder extends RecyclerView.ViewHolder{
        TextView patientText;
        TextView patientTimeText;
        ImageView Image;
        int position;

        public PatientHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.main_img_listrow);
            patientText = itemView.findViewById(R.id.main_title_listrow);
            patientTimeText = itemView.findViewById(R.id.main_time_listrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(patientsData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Patient patient, int position){
            patientText.setText(patient.getFullName());
            patientTimeText.setText(patient.getArrivalTime());
            Image.setImageResource(R.drawable.logo);

            this.position = position;
        }
    }
}
