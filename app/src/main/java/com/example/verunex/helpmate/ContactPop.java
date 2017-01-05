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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactPop extends Activity {

    private Button acceptButton, cancelButton;
    private EditText email, number;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_popup);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        mFirebaseAuth = FirebaseAuth.getInstance();


        initControl();
    }

    private void initControl(){

        email = (EditText)findViewById(R.id.contactEmail);
        number = (EditText)findViewById(R.id.contactNumber);

        acceptButton = (Button)findViewById(R.id.acceptChangesButton);
        cancelButton = (Button)findViewById(R.id.cancelChangesButton);

        String id_key = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email.setText(dataSnapshot.child("email").getValue().toString());
                number.setText(dataSnapshot.child("number").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = email.getText().toString();
                String userNumber = number.getText().toString();

                if(userEmail.equals("")){
                    Toast.makeText(getBaseContext(), "Wypełnij pole Email!", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabaseReference.child("email").setValue(userEmail);
                }
                if(userNumber.equals("")){
                    Toast.makeText(getBaseContext(), "Wypełnij pole numer!", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabaseReference.child("number").setValue(userNumber);
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


