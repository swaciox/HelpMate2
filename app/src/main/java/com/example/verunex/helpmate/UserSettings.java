package com.example.verunex.helpmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSettings extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    //email
    private EditText emailEditText;
    private Button buttonChangeEmail;

    //password
    private EditText passwordEditText;
    private Button buttonChangePassword;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        emailEditText = (EditText)findViewById(R.id.userChangeEmail);
        buttonChangeEmail = (Button)findViewById(R.id.userChangeEmailButton);

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailEditText.getText().toString().isEmpty()){
                    final String newEmail = emailEditText.getText().toString().trim();

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String id = user.getUid();


                    user.updateEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id).child("email");
                                                            mDatabaseReference.setValue(newEmail);


                                                            Toast.makeText(getBaseContext(),"Zmieniono adres email! Na "+newEmail+" wysłano link aktywacyjny", Toast.LENGTH_SHORT).show();

                                                            FirebaseAuth.getInstance().signOut();
                                                            Intent i = new Intent(getBaseContext(), SplashScreenActivity.class);
                                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(i);
                                                        }
                                                    }
                                                });
                                    }else{

                                    }
                                }
                            });
                }else{
                    Toast.makeText(getBaseContext(), "Pole email jest puste!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        passwordEditText = (EditText)findViewById(R.id.userChangePassword);
        buttonChangePassword = (Button)findViewById(R.id.userChangePasswordButton);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!passwordEditText.getText().toString().isEmpty()) {
                    final String newPassword = passwordEditText.getText().toString().trim();
                    Log.v("newPassword", newPassword);

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getBaseContext(), "Hasło zostało zmienione!", Toast.LENGTH_SHORT).show();
                                    }else{
                                    }
                                }
                            });
                }else{

                    Toast.makeText(getBaseContext(), "Pole hasło jest puste!", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}
