package com.example.verunex.helpmate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterSubPage extends Fragment implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPassword2;


    private FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_sub_page, container, false);

        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) rootView.findViewById(R.id.registerEmailAddress);
        mPassword = (EditText) rootView.findViewById(R.id.registerPassword);
        mPassword2 = (EditText) rootView.findViewById(R.id.registerRepeatPassword);



        Button bt = (Button) rootView.findViewById(R.id.buttonSignUp);
        bt.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp:
                startRegister();
                break;
        }
    }

    public void startRegister(){
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String password2 = mPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getContext(),"Wypelnij pole email!",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(getContext(),"Wypelnij pole password!",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password2)) {
            Toast.makeText(getContext(),"Wypelnij pole powtorz haslo!",Toast.LENGTH_SHORT).show();
        }else {
            if(password.equals(password2)){
                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getContext(), "Zarejestrowano!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(),"Hasła roznia sie!",Toast.LENGTH_SHORT).show();
            }

        }


        }

    }



