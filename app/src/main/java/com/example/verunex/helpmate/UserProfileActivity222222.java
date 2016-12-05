package com.example.verunex.helpmate;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UserProfileActivity222222 extends AppCompatActivity {

    private ImageButton user_image;
    private EditText user_name, user_number;
    private Button mButton;

    private Uri mImageUri = null;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    private DatabaseReference mOldData;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        user_image = (ImageButton) findViewById(R.id.user_image);
        user_name = (EditText) findViewById(R.id.userName);
        user_number = (EditText) findViewById(R.id.userPhone);
        mButton = (Button) findViewById(R.id.button3);

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
                Picasso.with(getBaseContext()).load(user_image_uri).into(user_image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        mStorageReference = FirebaseStorage.getInstance().getReference().child("users_image");

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery_intent = new Intent();
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent, GALLERY_REQUEST);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
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

        if(mImageUri==null){
            user_image.setImageResource(R.drawable.person);
            mDatabaseReference.child(user_id).child("name").setValue(name);
            mDatabaseReference.child(user_id).child("number").setValue(number);
        }else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();

                user_image.setImageURI(mImageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
