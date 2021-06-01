package com.example.appointme.model.Doctor;

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

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    public static List<Doctor> doctorsData = new LinkedList<Doctor>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Doctor getDoctors(int position)
    {
        return doctorsData.get(position);
    }

    public void setDoctorsData(List<Doctor> doctors) {
        this.doctorsData = doctors;
        notifyDataSetChanged();
    }

    // Create DoctorHolder for the adapter.
    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_row, parent, false);
        DoctorHolder holder = new DoctorHolder(view);
        return holder;
    }

    // Bind data to the adapter.
    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor currentDoctor = doctorsData.get(position);
        holder.bindData(currentDoctor,position);
        holder.itemView.setTag(currentDoctor);
    }

    @Override
    public int getItemCount() {
        return doctorsData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Doctor doctor, View view);
    }

    //---------------DoctorHolder----------------//

    public static class DoctorHolder extends RecyclerView.ViewHolder{
        TextView doctorText;
        TextView doctorSubText;
        ImageView Image;
        int position;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.patient_img_listrow);
            doctorText = itemView.findViewById(R.id.patient_title_listrow);
            doctorSubText = itemView.findViewById(R.id.patient_sub_listrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(doctorsData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Doctor doctor, int position){
            doctorText.setText(doctor.getFullName());
            doctorSubText.setText(doctor.getEmail());
            Image.setImageResource(R.drawable.logo);
            /*if (patient.getImageURL() != null) {
                Picasso.get().load(patient.getImageURL()).placeholder(R.drawable.gamechangersimple).into(gameImage);
            }*/

            this.position = position;
        }
    }
}
