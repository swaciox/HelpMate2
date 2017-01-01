package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfilePop extends Activity {

    private ImageView user_image, exitImageView;
    private EditText user_name, user_number;
    private Button save, imageEdit;
    private Button categoriesChoiceButton;

    private Uri mImageUri = null;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    private DatabaseReference mOldData;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_user_profile);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        // categories listView
        String[] categories = {"Hydraulika", "Pomoc domowa", "Ogrodnictwo", "Remonty", "Elektryka"};

        /*ListView mListView= (ListView) findViewById(R.id.categoriesListView);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        mListView.setAdapter(listAdapter);
        */



        user_image = (ImageView) findViewById(R.id.userImage);
        user_name = (EditText) findViewById(R.id.userNameEdit);
        user_number = (EditText) findViewById(R.id.userPhoneEdit);
        save = (Button) findViewById(R.id.save);
        imageEdit = (Button) findViewById(R.id.userImageEdit);
        exitImageView = (ImageView) findViewById(R.id.exitImageView);
        exitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), UserProfileActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        categoriesChoiceButton = (Button) findViewById(R.id.categoriesChoiceButton);
        categoriesChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CategoriesChoice.class);
                startActivity(i);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id = mFirebaseAuth.getCurrentUser().getUid();

        mOldData = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id);
        mOldData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                user_name.setText(name);

                String number = dataSnapshot.child("number").getValue(String.class);
                user_number.setText(number);


                String user_image_uri = dataSnapshot.child("user_image").getValue(String.class);
                if(user_image_uri.isEmpty()){

                }else{
                    Picasso.with(getBaseContext()).load(user_image_uri).into(user_image);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        mStorageReference = FirebaseStorage.getInstance().getReference().child("users_image");

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery_intent = new Intent();
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent, GALLERY_REQUEST);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateProfile();
            }
        });


    }

    private void startCreateProfile() {

        final String name = user_name.getText().toString().trim();
        final String number = user_number.getText().toString().trim();

        final String user_id = mFirebaseAuth.getCurrentUser().getUid();

        if (mImageUri == null) {
            user_image.setImageResource(R.drawable.person);
            mDatabaseReference.child(user_id).child("name").setValue(name);
            mDatabaseReference.child(user_id).child("number").setValue(number);
        } else {
            StorageReference filepath = mStorageReference.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseReference.child(user_id).child("name").setValue(name);
                    mDatabaseReference.child(user_id).child("number").setValue(number);
                    mDatabaseReference.child(user_id).child("user_image").setValue(downloadUri);

                }
            });
        }
    }
}



