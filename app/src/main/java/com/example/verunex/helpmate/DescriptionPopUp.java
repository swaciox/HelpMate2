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

public class DescriptionPopUp extends Activity {

    private EditText mEditText;
    private Button acceptButton, cancelButton;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_popup);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        mEditText = (EditText)findViewById(R.id.descriptionUser);
        acceptButton = (Button)findViewById(R.id.acceptChangesButton);
        cancelButton = (Button)findViewById(R.id.cancelChangesButton);

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key).child("desc");

        oldData();

        acceptButtonClick();




    }

    private void acceptButtonClick() {
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = mEditText.getText().toString();
                mDatabaseReference.setValue(desc);
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

    private void oldData() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String desc = dataSnapshot.getValue().toString();
                mEditText.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}