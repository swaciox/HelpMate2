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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceProviderPop extends Activity{

    private Button acceptButton, cancelButton;
    private TextView infoText;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private RandomValues mRandomValues;

    String temp;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_back_press);

        mRandomValues = new RandomValues();

        acceptButton = (Button) findViewById(R.id.acceptButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        infoText = (TextView)findViewById(R.id.infoText);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4));

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = mFirebaseAuth.getCurrentUser().getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

        infoText.setText("Czy na pewno chcesz zostać wykonawcą?");

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference user = mDatabaseReference;
                user.child("service_state").setValue("true");

                for (int i = 1; i <=23; i++) {
                    String temp = "sub"+i;
                    user.child("categories").child(temp).child("state").setValue("false");

                    String desc = mRandomValues.subcategoriesDes(i-1);
                    user.child("categories").child(temp).child("description").setValue(desc);
                }

                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}


