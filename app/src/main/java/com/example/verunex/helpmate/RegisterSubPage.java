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


                                String subCategory = randomC(random_category);
                                //mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Offers2").child(random_category).child(subCategory);
                                //DatabaseReference curent_user = mDatabaseReference.push();

                                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);
                                DatabaseReference curent_user = mDatabaseReference;
                                curent_user.child("name").setValue("");
                                curent_user.child("address").setValue("");
                                curent_user.child("number").setValue("");
                                curent_user.child("user_image").setValue("");
                                curent_user.child("email").setValue(email);
                                curent_user.child("service_state").setValue("false");
                                curent_user.child("user_id").setValue(id_key);
                                curent_user.child("desc").setValue("");

                                for (int i = 1; i <=23; i++){
                                    String temp = "sub"+i;
                                    curent_user.child("categories").child(temp).child("state").setValue("false");

                                    String desc = mRandomValues.subcategoriesDes(i-1);
                                    curent_user.child("categories").child(temp).child("description").setValue(desc);

                                }

                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();



                                /*curent_user.child("user_id").setValue(id_key);
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

    public String randomC(String s){
        Random mRandom = new Random();
        int number_category = mRandom.nextInt();
        String[] tab = new String[0];

        if(s.equals("hydraulika")){
           number_category = mRandom.nextInt(1);
            tab = new String[]{"naprawa_wyciekow",
                    "wymiana_armatury",};
        }else if (s.equals("opieka")){
            number_category = mRandom.nextInt(3);
            tab = new String[]{"opieka_do_dzieci",
                    "opieka_do_osob_starszych",
                    "opieka_dzieci_i_osob_niepelnosprawnych",
                    "wyprowadzanie_zwierzat"};

        }else if (s.equals("ogrodnictwo")){

            number_category = mRandom.nextInt(2);
            tab = new String[]{ "koszenie_trawy",
                    "prace_porzadkowe",
                    "pielegnacja_ogrodu"};
        }else if (s.equals("pomoc_domowa")){
            number_category = mRandom.nextInt(2);
            tab = new String[]{"sprzatanie",
                    "prasowanie",
                    "mycie_okien"};
        }else if (s.equals("remonty")){
            number_category = mRandom.nextInt(3);
            tab = new String[]{ "malowanie",
                    "tapetowanie",
                    "kladzenie_kafelek",
                    "kladzenie_paneli_podlogowych"};
        }else if (s.equals("elektryka")){
            number_category = mRandom.nextInt(1);
            tab = new String[]{ "instalacje_elektryczne",
                    "naprawa_awaryjna"};
        }else if (s.equals("pomoc_naukowa")){
            number_category = 0;
            tab = new String[]{  "korepetycje"};
        }else if (s.equals("naprawa_urzadzen")){
            number_category = mRandom.nextInt(3);
            tab = new String[]{ "naprawa_drobnego_AGD",
                    "naprawa_AGD",
                    "naprawa_RTV",
                    "naprawa_komputerow_laptopow"};
        }

        return tab[number_category];
    }

}




