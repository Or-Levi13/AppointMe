package com.example.appointme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointme.model.Doctor.Doctor;
import com.example.appointme.model.Model;
import com.example.appointme.model.Patient.Patient;
import com.example.appointme.model.User.User;


public class SignUpFragment extends Fragment {

    View view;
    EditText email;
    EditText password;
    EditText re_password;
    EditText fullName;
    Spinner userType;
    TextView signInBtn;
    Button signUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        email = view.findViewById(R.id.signup_email_input);
        password = view.findViewById(R.id.signup_password_input);
        re_password = view.findViewById(R.id.signup_repassword_input);
        userType = view.findViewById(R.id.signup_usertype_list);
        signInBtn = view.findViewById(R.id.signup_signin_btn);
        signUpBtn = view.findViewById(R.id.signup_confirm_btn);
        fullName = view.findViewById(R.id.signup_name_input);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
            }
        });

        String[] types = new String[]{"Select Entry Type", "Patient", "Doctor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        userType.setAdapter(adapter);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString();
                String user_pass = password.getText().toString();
                String user_repass = re_password.getText().toString();
                String user_type = userType.getSelectedItem().toString();
                String user_name = fullName.getText().toString();
                if (user_email.equals("") || user_pass.equals("") || userType.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                }
                if (!user_pass.equals(user_repass)) {
                    Toast.makeText(getActivity(),"Please Re-enter the correct Password",Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    if (user_type.equals("Doctor")){
                        user = new Doctor(user_email,user_name,user_type);
                    }
                    if (user_type.equals("Patient")){
                        user = new Patient(user_email,user_name,user_type);
                    }
                    Model.instance.signUpFB(user,user_pass);
                    Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
                }

            }});

        return view;
    }
}