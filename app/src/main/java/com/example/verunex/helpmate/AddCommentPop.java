package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_key = mFirebaseAuth.getCurrentUser().getUid();

                String desc = mEditText.getText().toString();

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(id_postion);

                DatabaseReference comment = mDatabaseReference;
                if(!desc.isEmpty()){
                    comment.child(id_key).setValue(desc);

                }else{
                    Toast.makeText(getBaseContext(),"Pusty EDITTEXT!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
