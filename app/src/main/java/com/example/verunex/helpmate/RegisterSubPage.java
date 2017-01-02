package com.example.verunex.helpmate;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class RegisterSubPage extends Fragment implements View.OnClickListener{

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPassword2;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private ProgressDialog mProgressDialog;

    //Random
    RandomValues mRandomValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRandomValues = new RandomValues();


        View rootView = inflater.inflate(R.layout.register_sub_page, container, false);

        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

       // mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgressDialog = new ProgressDialog(getContext());

        mEmail = (EditText) rootView.findViewById(R.id.registerEmailAddress);
        mPassword = (EditText) rootView.findViewById(R.id.registerPassword);
        mPassword2 = (EditText) rootView.findViewById(R.id.registerRepeatPassword);

        Button bt = (Button) rootView.findViewById(R.id.buttonSignUp);
        bt.setOnClickListener(this);

        return rootView;
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
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String password2 = mPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getContext(),"Wypełnij pole e-mail!",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(getContext(),"Wypełnij pole hasło!",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password2)) {
            Toast.makeText(getContext(),"Wypełnij pole powtórz hasło!",Toast.LENGTH_SHORT).show();
        }else {
            if(password.equals(password2)){
                mProgressDialog.setMessage("Rejestruję...");
                mProgressDialog.show();
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                String id_key = mFirebaseAuth.getCurrentUser().getUid();

                                String random_name = mRandomValues.randomName();
                                String random_number = mRandomValues.randomNumber();
                                String random_category = mRandomValues.randomCategory();
                                String random_image = mRandomValues.randomImage();
                                String random_rate = mRandomValues.randomRating();

                                //mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(random_category);
                                //DatabaseReference curent_user = mDatabaseReference.push();

                                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);
                                DatabaseReference curent_user = mDatabaseReference;
                                curent_user.child("name").setValue("");
                                curent_user.child("address").setValue("");
                                curent_user.child("number").setValue("");
                                curent_user.child("user_image").setValue("");
                                curent_user.child("email").setValue(email);

                                for (int i = 1; i <=23; i++){
                                    String temp = "sub"+i;
                                    curent_user.child("categories").child(temp).child("state").setValue("false");

                                    String desc = mRandomValues.subcategoriesDes(i-1);
                                    curent_user.child("categories").child(temp).child("description").setValue(desc);

                                }

                               /*
                                curent_user.child("user_id").setValue(id_key);
                                curent_user.child("email").setValue(email);
                                curent_user.child("name").setValue(random_name);
                                curent_user.child("category").setValue(random_category);
                                curent_user.child("number").setValue(random_number);
                                curent_user.child("image").setValue(random_image);
                                curent_user.child("rate").setValue(random_rate);
                                curent_user.child("description").setValue("Oferuję pełen zakres usług");
                                */
                            mProgressDialog.dismiss();

                            Toast.makeText(getContext(), "Zarejestrowano!", Toast.LENGTH_SHORT).show();
                        }else{
                            mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Podany użytkownik już istnieje!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(),"Podane hasła różnią się!",Toast.LENGTH_SHORT).show();
            }

        }


        }

}




