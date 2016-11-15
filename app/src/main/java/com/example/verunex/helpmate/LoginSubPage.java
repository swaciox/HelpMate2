package com.example.verunex.helpmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSubPage extends Fragment implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sub_page, container, false);

        //FIrebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mEmail = (EditText) rootView.findViewById(R.id.registerEmailAddress);
        mPassword = (EditText) rootView.findViewById(R.id.registerPassword);

        Button bt = (Button) rootView.findViewById(R.id.buttonSignIn);
        bt.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignIn:
                    startLogin();
                break;
        }
    }

    public void startLogin(){

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUser();
                    }else{
                        Toast.makeText(getContext(), "Problem z logowaniem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(),"Uzupelnij wszystkie pola!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUser(){

        final String user_id = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast.makeText(getContext(), user_id , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Taki użytkownik nie istnieje!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }












}
