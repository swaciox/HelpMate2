package com.example.verunex.helpmate;

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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                final String number = viewHolder.setNumber(model.getNumber());
                final String name = viewHolder.setName(model.getName());
                final String category = viewHolder.setCategory(model.getCategory());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleuser = new Intent(MainActivity.this, SingleUser.class);
                        singleuser.putExtra("user_number", number);
                        singleuser.putExtra("user_name", name);
                        singleuser.putExtra("user_category", category);
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

        Button btn;

        public UserViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            btn = (Button)mView.findViewById(R.id.TENBUTTON);

        }

        public String setName(String name){
            TextView user_name = (TextView) mView.findViewById(R.id.name);
            user_name.setText(name);
            return name;
        }
        public String setNumber (String number){
            TextView user_number = (TextView) mView.findViewById(R.id.number);
            user_number.setText(number);
            return number;
        }
        public String setCategory(String category){
            TextView user_category = (TextView) mView.findViewById(R.id.category);
            user_category.setText(category);
            return category;
        }

       /* public void setImage(String image){
            ImageView user_img = (ImageView) mView.findViewById(R.id.image);
            user_img.sets
        }*/
    }
}
