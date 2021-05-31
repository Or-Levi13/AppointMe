package com.example.appointme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appointme.model.Model;

public class LoginFragment extends Fragment {

    View view;

    Button SignUpBtn;
    Button signInBtn;
    Button forgotPass;
    EditText email;
    EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        signInBtn = view.findViewById(R.id.signin_login_btn);
        email = view.findViewById(R.id.signin_email_input);
        password = view.findViewById(R.id.signin_password_input);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if (userEmail.equals("") && userPassword.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    Model.instance.logInFB(userEmail, userPassword, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (result) {
                                //Navigation.findNavController(view).navigate(R.id.action_signin_to_mainFeed);
                                Toast.makeText(getActivity(), "Success to login", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}