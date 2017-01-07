package com.example.verunex.helpmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSubPage extends Fragment implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;

    private TextView mTextView;

    private ProgressDialog mProgressDialog;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    String tempo="false";

    Boolean check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sub_page, container, false);

        //Progress
        mProgressDialog = new ProgressDialog(getContext());

        //FIrebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        mEmail = (EditText) rootView.findViewById(R.id.registerEmailAddress);
        mPassword = (EditText) rootView.findViewById(R.id.registerPassword);

        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mTextView.setOnClickListener(this);

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
            case R.id.textView:
                startActivity(new Intent(getContext(),ForgetPasswordPop.class));
                break;

        }
    }

    public void startLogin(){

        mProgressDialog.setMessage("Logowanie...");
        mProgressDialog.show();

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUser();
                    }else{
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), "Podane konto nie istnieje!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            mProgressDialog.dismiss();
            Toast.makeText(getContext(),"Uzupełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUser(){
        final String id = mFirebaseAuth.getCurrentUser().getUid();


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                check = dataSnapshot.hasChild(id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(check=false){
            mProgressDialog.dismiss();
            Toast.makeText(getContext(),"Podane konto nie istnieje!", Toast.LENGTH_SHORT ).show();
        }else {
            if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                mProgressDialog.dismiss();
                Intent i = new Intent(getContext(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);



            }else{
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Zweryfukuj adres email!", Toast.LENGTH_SHORT ).show();
            }
        }


    }












}
