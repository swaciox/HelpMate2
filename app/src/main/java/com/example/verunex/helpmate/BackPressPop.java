package com.example.verunex.helpmate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class BackPressPop extends Activity{

    private Button acceptButton, cancelButton;
    private Button okButton;
    private TextView infoText;
    private int temp;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.pop_back_press);

            temp = getIntent().getIntExtra("temp",0);


            acceptButton = (Button) findViewById(R.id.acceptButton);
            cancelButton = (Button) findViewById(R.id.cancelButton);
            okButton = (Button)findViewById(R.id.okButton);
            okButton.setVisibility(View.INVISIBLE);

            infoText = (TextView)findViewById(R.id.infoText);

            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

            int width = mDisplayMetrics.widthPixels;
            int height = mDisplayMetrics.heightPixels;

            getWindow().setLayout((int)(width*.8),(int)(height*.3));
            if(temp == 0 ) {
                infoText.setText("Czy na pewno chcesz wrócić do głównego ekranu?");

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            } else if(temp == 1 ){
                infoText.setText("Czy na pewno chcesz się wylogować?");

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }else if(temp == 3){

                infoText.setText("Zmiany nie zostały zapisane, czy na pewno chcesz je odrzucić?");

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }else if (temp == 4 ){
                infoText.setText("Tu bedzie opis informacja do profilu wykonawcy");

                cancelButton.setVisibility(View.INVISIBLE);
                acceptButton.setVisibility(View.INVISIBLE);
                okButton.setText("OK");
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }


}


