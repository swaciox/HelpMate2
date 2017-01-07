package com.example.verunex.helpmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyCommentServiceUser extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment_service_user);

        mCommentList = (RecyclerView)findViewById(R.id.comment_view);
        mCommentList.setHasFixedSize(true);
        mCommentList.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(id_key);

        FirebaseRecyclerAdapter<Comment, CommentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.comment_row,
                CommentViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, final int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setUserImage(getApplicationContext(),model.getUser_image());
                viewHolder.setRate(model.getRate());
                viewHolder.setDate(model.getDate());

                String rate = model.getRate();
                String comment_id = model.getUser_id();


                //temp = temp + Float.parseFloat(rate);
                //Log.v("temp ", String.valueOf(temp));

            }
        };

        mCommentList.setAdapter(firebaseRecyclerAdapter);

    }


}

