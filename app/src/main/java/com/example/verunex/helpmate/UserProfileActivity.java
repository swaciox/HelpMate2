package com.example.verunex.helpmate;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView user_name, user_address, user_number;
    private Button mButton;
    private ImageView user_image;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        user_name = (TextView)findViewById(R.id.userName);
        user_number = (TextView)findViewById(R.id.userPhone);
        user_address = (TextView)findViewById(R.id.userAddress);
        user_image = (ImageView)findViewById(R.id.userImage);

        mButton = (Button)findViewById(R.id.buttonEdit);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditProfilePop.class);
                startActivity(i);
            }
        });


        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //nazwa z firebase
                String name = dataSnapshot.child("name").getValue(String.class);
                if(name.isEmpty()){
                    //user_name.setText("uzupelnij");
                }else{
                    user_name.setText(name);
                }
                //adres z firebase
                String address = dataSnapshot.child("address").getValue(String.class);
                if(address.isEmpty()){
                    //user_address.setText("Zaktualizuj swoje dane");
                }else {
                    user_address.setText(address);
                }

                //numer z firebase
                String number = dataSnapshot.child("number").getValue(String.class);
                if(number.isEmpty()){
                    //user_number.setText("Uzupelnij!");
                }else{
                    user_number.setText(number);
                }


                // image z firebase
                String user_image_uri = dataSnapshot.child("user_image").getValue(String.class);
                if(user_image_uri.isEmpty()){
                    user_image.setImageResource(R.drawable.person);
                }else{
                    Picasso.with(getBaseContext()).load(user_image_uri).into(user_image);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
