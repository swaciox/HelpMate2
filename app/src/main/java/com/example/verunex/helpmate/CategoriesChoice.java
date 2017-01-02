package com.example.verunex.helpmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoriesChoice extends AppCompatActivity {

    private Button mButton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private CheckBox choice1, choice2, choice3, choice4, choice5, choice6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_choice);

        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key).child("categories");


        // CheckBoxes
        CheckBox sub1 = (CheckBox) findViewById(R.id.sub1);
        CheckBox sub2 = (CheckBox) findViewById(R.id.sub2);
        CheckBox sub3 = (CheckBox) findViewById(R.id.sub3);
        CheckBox sub4 = (CheckBox) findViewById(R.id.sub4);
        CheckBox sub5 = (CheckBox) findViewById(R.id.sub5);
        CheckBox sub6 = (CheckBox) findViewById(R.id.sub6);
        CheckBox sub7 = (CheckBox) findViewById(R.id.sub7);
        CheckBox sub8 = (CheckBox) findViewById(R.id.sub8);
        CheckBox sub9 = (CheckBox) findViewById(R.id.sub9);
        CheckBox sub10 = (CheckBox) findViewById(R.id.sub10);
        CheckBox sub11 = (CheckBox) findViewById(R.id.sub11);
        CheckBox sub12 = (CheckBox) findViewById(R.id.sub12);
        CheckBox sub13 = (CheckBox) findViewById(R.id.sub13);
        CheckBox sub14 = (CheckBox) findViewById(R.id.sub14);
        CheckBox sub15 = (CheckBox) findViewById(R.id.sub15);
        CheckBox sub16 = (CheckBox) findViewById(R.id.sub16);
        CheckBox sub17 = (CheckBox) findViewById(R.id.sub17);
        CheckBox sub18 = (CheckBox) findViewById(R.id.sub18);
        CheckBox sub19 = (CheckBox) findViewById(R.id.sub19);
        CheckBox sub20 = (CheckBox) findViewById(R.id.sub20);
        CheckBox sub21 = (CheckBox) findViewById(R.id.sub21);
        CheckBox sub22 = (CheckBox) findViewById(R.id.sub22);
        CheckBox sub23 = (CheckBox) findViewById(R.id.sub23);

        final CheckBox[] checkBoxes = {sub1, sub2,sub3,sub4,sub5,sub6,sub7,sub8,sub9,sub10,
                sub11,sub12,sub13,sub14,sub15,sub16,sub17,sub18,sub19,sub20,sub21,sub22,sub23};


        String[] checkboxes = new String [23];
        for(int i = 0; i<checkboxes.length; i++){
            int n = i+1;
            String name = "sub"+n;
            checkboxes[i]= name;
        }

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < 23 ; i++){
                    int n = i+1;
                    String temp = "sub"+n;
                    String state = dataSnapshot.child(temp).child("state").getValue().toString();

                    Log.v ("Komunikat "+temp , state);

                    if (state.equals("true")){
                        checkBoxes[i].setChecked(true);
                    }else{
                        checkBoxes[i].setChecked(false);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mButton = (Button)findViewById(R.id.acceptCategories);

       mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //String[] checkBoxTexts = new String[6];
               for (int i = 0; i < 23; i++) {
                   int n = i+1;
                   String temp = "sub"+n;
                   if (checkBoxes[i].isChecked()) {
                       mDatabaseReference.child(temp).child("state").setValue("true");
                   }else{
                       mDatabaseReference.child(temp).child("state").setValue("false");
                   }
               }

               /*Intent dataCheckbox = new Intent (getBaseContext(), EditUserProfile.class);
               dataCheckbox.putExtra("dataCheckBoxTexts",checkBoxTexts);
               setResult(2, dataCheckbox);*/
               //startActivity(dataCheckbox);
               finish();

           }
       });
    }

    private void initControl(){

        CheckBox sub1 = (CheckBox) findViewById(R.id.sub1);
        CheckBox sub2 = (CheckBox) findViewById(R.id.sub2);
        CheckBox sub3 = (CheckBox) findViewById(R.id.sub3);
        CheckBox sub4 = (CheckBox) findViewById(R.id.sub4);
        CheckBox sub5 = (CheckBox) findViewById(R.id.sub5);
        CheckBox sub6 = (CheckBox) findViewById(R.id.sub6);
        CheckBox sub7 = (CheckBox) findViewById(R.id.sub7);
        CheckBox sub8 = (CheckBox) findViewById(R.id.sub8);
        CheckBox sub9 = (CheckBox) findViewById(R.id.sub9);
        CheckBox sub10 = (CheckBox) findViewById(R.id.sub10);
        CheckBox sub11 = (CheckBox) findViewById(R.id.sub11);
        CheckBox sub12 = (CheckBox) findViewById(R.id.sub12);
        CheckBox sub13 = (CheckBox) findViewById(R.id.sub13);
        CheckBox sub14 = (CheckBox) findViewById(R.id.sub14);
        CheckBox sub15 = (CheckBox) findViewById(R.id.sub15);
        CheckBox sub16 = (CheckBox) findViewById(R.id.sub16);
        CheckBox sub17 = (CheckBox) findViewById(R.id.sub17);
        CheckBox sub18 = (CheckBox) findViewById(R.id.sub18);
        CheckBox sub19 = (CheckBox) findViewById(R.id.sub19);
        CheckBox sub20 = (CheckBox) findViewById(R.id.sub20);
        CheckBox sub21 = (CheckBox) findViewById(R.id.sub21);
        CheckBox sub22 = (CheckBox) findViewById(R.id.sub22);
        CheckBox sub23 = (CheckBox) findViewById(R.id.sub23);
    }
}
