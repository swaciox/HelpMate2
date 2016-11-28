package com.example.verunex.helpmate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserFavouriteActivity extends AppCompatActivity {

    private ListView mListView;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference UserDataReference;

    private FirebaseListAdapter mFirebaseListAdapter;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favourite);

        mListView = (ListView) findViewById(R.id.testlist);

        mFirebaseAuth = FirebaseAuth.getInstance();

        final String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserFavourite").child(id_key);

        /*mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long s = dataSnapshot.getChildrenCount();
                String id = dataSnapshot.child(id_key).toString();
                String a = String.valueOf(id);
                Log.v ("Komunikat ", id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mFirebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                R.layout.users_list,
                mDatabaseReference

        ) {

            @Override
            protected void populateView(final View v, final String model, final int position) {

                final TextView user_name = (TextView) v.findViewById(R.id.name);
                final TextView user_category = (TextView)v.findViewById(R.id.category);
                final ImageButton user_number = (ImageButton)v.findViewById(R.id.call);
                final ImageView user_image = (ImageView)v.findViewById(R.id.image);

                final RatingBar user_rate = (RatingBar)v.findViewById(R.id.ratingBar);
                final CheckBox user_favourite = (CheckBox)v.findViewById(R.id.checkBox);

                user_favourite.setChecked(true);

                UserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(model);

                UserDataReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Visibilty -> false
                        //user_rate.setVisibility(View.INVISIBLE);
                        //user_favourite.setVisibility(View.INVISIBLE);

                        //checkbox favourite

                    user_favourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(user_favourite.isChecked()==false){
                                mDatabaseReference.child(model).removeValue();
                                Toast.makeText(getBaseContext(), "Usunieto z ulubionych!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                        //pole rate
                        String rate = dataSnapshot.child("rate").getValue(String.class);
                        user_rate.setRating(Float.parseFloat(rate));

                        // pole name
                        String name = dataSnapshot.child("name").getValue(String.class);
                        user_name.setText(name);

                        //pole image
                        String image = dataSnapshot.child("image").getValue(String.class);
                        Picasso.with(getBaseContext()).load(image).into(user_image);

                        //funkcja call
                        final String number = dataSnapshot.child("number").getValue(String.class);
                        user_number.setOnClickListener(new View.OnClickListener() {
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

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(model).hasChild(id_key)) {
                            String name = "asdasd";
                            textView.setText(name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        };
    mListView.setAdapter(mFirebaseListAdapter);}
}




                    /*datareferenceeee = FirebaseDatabase.getInstance().getReference().child("UserFavourite");

                    datareferenceeee.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child(id_key).getValue(String.class);
                            textView.setText(name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                //final String s = textView.getText().toString().trim();

                /*v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent (getBaseContext(), sialala.class);
                        i.putExtra("s",s);
                        startActivity(i);
                    }
                });*/
            //}
        //};

       /* mListView.setAdapter(mFirebaseListAdapter);
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseListAdapter.cleanup();
    }*/
//}
