package com.example.verunex.helpmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class FragmentSubPage2 extends Fragment {

    private TextView user_comment;
    private ImageView user_image;
    private RatingBar user_rate;

    private Button addComment;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private FirebaseListAdapter mFirebaseListAdapter;

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sub_page2, container, false);

        user_comment = (TextView)view.findViewById(R.id.comment);
        user_image = (ImageView)view.findViewById(R.id.imageComment);
        user_rate = (RatingBar)view.findViewById(R.id.ratingBar);

        addComment = (Button)view.findViewById(R.id.addComment);


        mListView = (ListView) view.findViewById(R.id.commentListView);

        mFirebaseAuth = FirebaseAuth.getInstance();


        final String id_key = mFirebaseAuth.getCurrentUser().getUid();

        //retrieve data
        final String id_position = this.getArguments().getString("id_position");
        Log.v("frag2id_position",id_position);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddCommentPop.class);
                i.putExtra("id_position", id_position);
                startActivity(i);
            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comment").child(id_position);

        mFirebaseListAdapter = new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                R.layout.user_comment_row,
                mDatabaseReference
        ) {
            @Override
            protected void populateView(View v, final String model, int position) {
                //String id_user_comment = v;
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String a = dataSnapshot.child(model);
                        //Log.v(" ",a);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };


        mListView.setAdapter(mFirebaseListAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDesc(String desc){
            TextView comment = (TextView) mView.findViewById(R.id.comment);
            comment.setText(desc);
        }

       /* public void setImage(Context ctx, String image){
            ImageView user_image = (ImageView) mView.findViewById(R.id.imageComment);
            Picasso.with(ctx).load(image).into(user_image);
        }*/
        // public void setRate(String rate){
        //     RatingBar user_rate = (RatingBar) mView.findViewById(R.id.ratingBar);
        //    user_rate.setRating(Float.parseFloat(rate));
        // }
        //public String setDescription(String description){
        //   return description;
        //}
    }

}
