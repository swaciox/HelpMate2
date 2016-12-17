package com.example.verunex.helpmate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserFavouriteActivity extends AppCompatActivity {

    private RecyclerView mListView;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference UserDataReference;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favourite);

        mListView = (RecyclerView) findViewById(R.id.RecyclerFavouriteList);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mFirebaseAuth = FirebaseAuth.getInstance();

        final String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserFavourite").child(id_key);

        FirebaseRecyclerAdapter<User, UserProfileViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserProfileViewHolder>(
                User.class,
                R.layout.users_list,
                UserProfileViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final UserProfileViewHolder viewHolder, final User model, int position) {
                viewHolder.setCategory(model.getCategory());
                viewHolder.setName(model.getName());
                viewHolder.setNumber(model.getNumber());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setRate(model.getRate());
                viewHolder.setUser_id(model.getUser_id());
                viewHolder.setEmail(model.getEmail());

                final String email = model.getEmail();
                final String number = model.getNumber();
                final String name = model.getName();
                final String category = model.getCategory();
                final String image = model.getImage();
                final String rate = model.getRate();
                //final String description = viewHolder.setDescription(model.getDescription());
                final String user_id = model.getUser_id();


                final String id_position = getRef(position).getKey();

                final TextView user_name = (TextView) findViewById(R.id.name);
                final TextView user_category = (TextView)findViewById(R.id.category);
                final ImageButton user_number = (ImageButton)findViewById(R.id.call);
                final ImageView user_image = (ImageView)findViewById(R.id.image);
                final RatingBar user_rate = (RatingBar)findViewById(R.id.commentRatingBar);
                final CheckBox user_favourite = (CheckBox)findViewById(R.id.checkBox);

                viewHolder.favouriteBox.setChecked(true);
                viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_like);

                final String key = getRef(position).getKey();

                viewHolder.favouriteBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewHolder.favouriteBox.isChecked()==false){
                            mDatabaseReference.child(key).removeValue();
                            Toast.makeText(getBaseContext(), "Usunięto z ulubionych!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleuser = new Intent(getBaseContext(), SingleUserActivity.class);
                        singleuser.putExtra("user_name", name);
                        singleuser.putExtra("user_category", category);
                        singleuser.putExtra("user_image", image);
                        singleuser.putExtra("user_rate", rate);
                        singleuser.putExtra("id_position", id_position);
                        //singleuser.putExtra("user_description", description);
                        startActivity(singleuser);

                        Bundle bundle = new Bundle();
                        bundle.putString("user_number", number);
                        FragmentSubPage1 fragobj = new FragmentSubPage1();
                        fragobj.setArguments(bundle);

                        /*Bundle bundle1 = new Bundle();
                        bundle1.putString("id_position", id_position);
                        FragmentSubPage2 objSubPage2 = new FragmentSubPage2();
                        objSubPage2.setArguments(bundle1);
*/
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

                viewHolder.btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", number);
                        smsIntent.putExtra("sms_body","Witam pisze z serwisu HelpMate!\n");
                        startActivity(smsIntent);
                    }
                });

                viewHolder.btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Zapytanie HelpMate");
                        intent.putExtra(Intent.EXTRA_TEXT, "Witam pisze z portalu HelpMate");
                        startActivity(Intent.createChooser(intent, ""));
                    }
                });

               /* UserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(category).child(key); // child model1

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
                                    mDatabaseReference.child(key).removeValue();
                                    Toast.makeText(getBaseContext(), "Usunięto z ulubionych!", Toast.LENGTH_SHORT).show();
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
                */
            }
        };
    mListView.setAdapter(mFirebaseRecyclerAdapter);}
}

