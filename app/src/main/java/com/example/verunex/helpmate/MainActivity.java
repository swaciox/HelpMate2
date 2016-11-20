package com.example.verunex.helpmate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mUsersList;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");


        mUsersList = (RecyclerView) findViewById(R.id.users_row);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, UserViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.users_list,
                UserViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder, final User model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setNumber(model.getNumber());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setRate(model.getRate());

                final String number = viewHolder.setNumber(model.getNumber());
                final String name = viewHolder.setName(model.getName());
                final String category = viewHolder.setCategory(model.getCategory());
                final String image = viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleuser = new Intent(MainActivity.this, SingleUserTest.class);
                        //singleuser.putExtra("user_number", number);
                        singleuser.putExtra("user_name", name);
                        singleuser.putExtra("user_category", category);
                        singleuser.putExtra("user_image", image);
                        startActivity(singleuser);

                    }
                });
                viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                });
            }
        };
        mUsersList.setAdapter(mFirebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        ImageButton btn;

        public UserViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            btn = (ImageButton)mView.findViewById(R.id.call);

        }

        public String setName(String name){
            TextView user_name = (TextView) mView.findViewById(R.id.name);
            user_name.setText(name);
            return name;
        }
        public String setNumber (String number){
            return number;
        }

        public String setCategory(String category){
            TextView user_category = (TextView) mView.findViewById(R.id.category);
            user_category.setText(category);
            return category;
        }

        public String setImage(Context ctx, String image){
            ImageView user_img = (ImageView) mView.findViewById(R.id.image);
            Picasso.with(ctx).load(image).into(user_img);
            return image;
        }
        public String setRate(String rate){
            RatingBar user_rate = (RatingBar) mView.findViewById(R.id.ratingBar);
            user_rate.setRating(Float.parseFloat(rate));
            return rate;
        }
    }
}
