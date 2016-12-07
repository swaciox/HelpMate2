package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCommentPop extends Activity {

    private EditText mEditText;
    private Button mButton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private DatabaseReference UserData;

    private RatingBar mRatingBar;

    private float value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_popup);

        final String id_postion = getIntent().getExtras().getString("id_position");
        Log.v("id_postion", id_postion);

        mFirebaseAuth = FirebaseAuth.getInstance();


        final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .3));

        mEditText = (EditText)findViewById(R.id.commentEditText);
        mButton = (Button)findViewById(R.id.addCommentPopup);

        mRatingBar = (RatingBar)findViewById(R.id.ratingBar2);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value = rating;
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_key = mFirebaseAuth.getCurrentUser().getUid();

                String desc = mEditText.getText().toString();

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(id_postion).push();

                final DatabaseReference comment = mDatabaseReference;
                if(!desc.isEmpty()){
                    comment.child("desc").setValue(desc);
                    comment.child("user_id").setValue(id_key);

                    // rate convert
                    String convertValue = "" + Float.toString(value);
                    comment.child("rate").setValue(convertValue);


                    UserData = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

                    UserData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String user_image = dataSnapshot.child("user_image").getValue(String.class);
                            if (user_image.isEmpty()){
                                user_image = "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/users_image%2Fperson.png?alt=media&token=4b1a210e-b333-40fe-b155-6973f3e9d00e";
                            }
                            comment.child("name").setValue(name);
                            comment.child("user_image").setValue(user_image);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(getBaseContext(),"Pusty EDITTEXT!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
