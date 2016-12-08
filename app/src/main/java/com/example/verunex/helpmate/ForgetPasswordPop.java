package com.example.verunex.helpmate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordPop extends Activity{

    private EditText mEmail;
    private Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window_forget_password);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        //Firebase
        final FirebaseAuth auth = FirebaseAuth.getInstance();


        //find
        mEmail = (EditText)findViewById(R.id.editText);
        bt = (Button)findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailAddress = mEmail.getText().toString().trim();

                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Wysłano hasło na podany adres e-mail: " + emailAddress , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getBaseContext(),"Błąd, spróbuj ponownie!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
}
