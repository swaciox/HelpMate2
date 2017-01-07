package com.example.verunex.helpmate;

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

public class UserSettings extends AppCompatActivity {

    //email
    private EditText emailEditText;
    private Button buttonChangeEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = (EditText)findViewById(R.id.userChangeEmail);
        buttonChangeEmail = (Button)findViewById(R.id.userChangeEmailButton);

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailEditText.getText().toString().isEmpty()){
                    final String newEmail = emailEditText.getText().toString().trim();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getBaseContext(),"Zmieniono adres email! Na "+newEmail, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });




    }
}
