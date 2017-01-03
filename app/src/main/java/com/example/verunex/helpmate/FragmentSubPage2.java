package com.example.verunex.helpmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentSubPage2 extends Fragment {

    /*private TextView user_comment;
    private ImageView user_image;
    private RatingBar user_rate;
    */

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private FirebaseListAdapter mFirebaseListAdapter;

    private RecyclerView mCommentList;
    private Button addComment;

    String id_key;


    float temp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_sub_page2, container, false);

        mCommentList = (RecyclerView) view.findViewById(R.id.comment_view);
        mCommentList.setHasFixedSize(true);
        //lub get activity
        mCommentList.setLayoutManager(new LinearLayoutManager(getContext()));



        addComment = (Button)view.findViewById(R.id.addComment);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            addComment.setVisibility(View.INVISIBLE);
        }else {
            id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();
            addComment.setVisibility(View.VISIBLE);
        }

        //retrieve data
        final String id_position = this.getArguments().getString("id_position");
        final String user_id = this.getArguments().getString("user_id");
        Log.v("frag2id_position",user_id);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddCommentPop.class);
                i.putExtra("user_id", user_id);
                i.putExtra("id_position", id_position);
                startActivity(i);
            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(user_id);

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
                viewHolder.setUserImage(getActivity(),model.getUser_image());
                viewHolder.setRate(model.getRate());

                String rate = model.getRate();
                String comment_id = model.getUser_id();

                if (comment_id.equals(id_key)){
                    addComment.setVisibility(View.INVISIBLE);
                    viewHolder.mImageButton.setVisibility(View.VISIBLE);
                    viewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference key = getRef(position);
                            key.removeValue();
                            addComment.setVisibility(View.VISIBLE);
                        }
                    });
                }else{
                    //addComment.setVisibility(View.VISIBLE);
                }
                temp = temp + Float.parseFloat(rate);
                Log.v("temp ", String.valueOf(temp));

            }
        };

        mCommentList.setAdapter(firebaseRecyclerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }

}
