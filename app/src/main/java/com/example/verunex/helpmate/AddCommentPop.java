package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import java.util.Calendar;

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
        final String user_id = getIntent().getExtras().getString("user_id");
        Log.v("id_postion", id_postion);

        mFirebaseAuth = FirebaseAuth.getInstance();


        final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        mEditText = (EditText)findViewById(R.id.commentEditText);
        mButton = (Button)findViewById(R.id.addCommentPopup);

        mRatingBar = (RatingBar)findViewById(R.id.ratingBar2);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                value = rating;
                Log.v("value", String.valueOf(value));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String id_key = mFirebaseAuth.getCurrentUser().getUid();

                String desc = mEditText.getText().toString();

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(id_postion).push();

                final DatabaseReference comment = mDatabaseReference;
                if(!desc.isEmpty()){
                    comment.child("desc").setValue(desc);
                    comment.child("user_id").setValue(id_key);


                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH)+1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    int minute = c.get(Calendar.MINUTE);
                    int hour = c.get(Calendar.HOUR);

                    String tempDay;
                    String tempMonth;

                    if (mDay <10){
                        tempDay = "0"+mDay;
                    }else{
                        tempDay = ""+ mDay;

                    }

                    if (mMonth <10){
                        tempMonth = "0"+mMonth;
                    }else{
                        tempMonth =""+ mMonth;
                    }

                    String date = mYear+"."+tempMonth+"."+tempDay;
                    comment.child("date").setValue(date);

                    // rate convert
                    String convertValue = "" + value+"f";
                    comment.child("rate").setValue(convertValue);


                    UserData = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);



                    UserData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           String name =dataSnapshot.child("name").getValue(String.class);
                            String user_image =dataSnapshot.child("user_image").getValue(String.class);
                            comment.child("name").setValue(name);
                            comment.child("user_image").setValue(user_image);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getBaseContext(),"Wypełnij pole komentarza!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
