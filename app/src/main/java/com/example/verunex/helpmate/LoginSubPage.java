package com.example.verunex.helpmate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginSubPage extends Fragment implements View.OnClickListener{


    private DatabaseReference mDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sub_page, container, false);
        Button bt = (Button) rootView.findViewById(R.id.buttonSignIn);
        bt.setOnClickListener(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignIn:
                    senddata();
                break;
        }
    }

    public void senddata(){

        DatabaseReference ref = mDatabaseReference.child("Name");
        ref.setValue("Marcin");




    }












}
