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

    private String cureent_user_id;
    private DatabaseReference getSubcategories;


    private String email;
    private String image;

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

        cureent_user_id = mFirebaseAuth.getCurrentUser().getUid();

        FirebaseRecyclerAdapter<User2, User2ProfileViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User2, User2ProfileViewHolder>(
                User2.class,
                R.layout.users_list,
                User2ProfileViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final User2ProfileViewHolder viewHolder, final User2 model, int position) {
                final String category = model.getCategory();

                String user_id_now = mFirebaseAuth.getCurrentUser().getUid();


                DatabaseReference newData = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(user_id_now);
                newData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        email = dataSnapshot.child("email").getValue().toString();
                        image = dataSnapshot.child("user_image").getValue().toString();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.setAddress(model.getAddress());
                viewHolder.setName(model.getName());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setNumber(model.getNumber());
                //viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setImage(getApplicationContext(),model.getUser_image());
                viewHolder.setRate(model.getRate());
                //viewHolder.setUser_id(model.getUser_id());
                viewHolder.setUid(model.getUid());
                viewHolder.setEmail(model.getEmail());
                //viewHolder.setDescription(model.getDescription());

                final String service_state = model.getService_state();
                final String email = model.getEmail();
                final String number = model.getNumber();
                final String name = model.getName();
                final String image = model.getUser_image();
                final String rate = model.getRate();
                final String desc = model.getDesc();
                final String user_id = model.getUid();
                final String address = model.getAddress();


                final String id_position = getRef(position).getKey();
                final String subcategory = model.getSubcategory();


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
                        singleuser.putExtra("Id_key", cureent_user_id);
                        singleuser.putExtra("user_name", name);
                        singleuser.putExtra("user_category", category);
                        singleuser.putExtra("user_image", image);
                        singleuser.putExtra("user_rate", rate);
                        singleuser.putExtra("id_position", id_position);
                        singleuser.putExtra("user_id", user_id);
                        singleuser.putExtra("number", number);
                        singleuser.putExtra("desc", desc);
                        singleuser.putExtra("email", email);
                        singleuser.putExtra("subcategory", subcategory);
                        singleuser.putExtra("address", address);

                        getSubcategories = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(cureent_user_id).child("categories");
                        getSubcategories.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(int i =1; i <=23; i++) {
                                    String temp = "sub" + i;

                                    String item = dataSnapshot.child(temp).child("description").getValue().toString();

                                    String subcategory = "";
                                    if (item.equals("naprawa wycieków")) {
                                        subcategory = "naprawa_wyciekow";
                                    } else if (item.equals("wymiana armatury")) {
                                        subcategory = "wymiana_armatury";
                                    } else if (item.equals("instalacje elektryczne")) {
                                        subcategory = "instalacje_elektryczne";
                                    } else if (item.equals("naprawa awaryjna")) {
                                        subcategory = "naprawa_awaryjna";
                                    } else if (item.equals("sprzątanie")) {
                                        subcategory = "sprzatanie";
                                    } else if (item.equals("prasowanie")) {
                                        subcategory = "prasowanie";
                                    } else if (item.equals("mycie okien")) {
                                        subcategory = "mycie_okien";
                                    } else if (item.equals("opieka do dzieci")) {
                                        subcategory = "opieka_do_dzieci";
                                    } else if (item.equals("opieka do osób starszych")) {
                                        subcategory = "opieka_do_osob_starszych";
                                    } else if (item.equals("opieka dzieci i osób niepełnosprawnych")) {
                                        subcategory = "opieka_dzieci_i_osob_niepelnosprawnych";
                                    } else if (item.equals("wyprowadzanie zwierząt")) {
                                        subcategory = "wyprowadzanie_zwierzat";
                                    } else if (item.equals("korepetycje")) {
                                        subcategory = "korepetycje";
                                    } else if (item.equals("koszenie trawy")) {
                                        subcategory = "koszenie_trawy";
                                    } else if (item.equals("prace porządkowe")) {
                                        subcategory = "prace_porzadkowe";
                                    } else if (item.equals("pielęgnacja ogrodu")) {
                                        subcategory = "pielegnacja_ogrodu";
                                    } else if (item.equals("naprawa drobnego AGD")) {
                                        subcategory = "naprawa_drobnego_AGD";
                                    } else if (item.equals("naprawa AGD")) {
                                        subcategory = "naprawa_AGD";
                                    } else if (item.equals("naprawa RTV")) {
                                        subcategory = "naprawa_RTV";
                                    } else if (item.equals("naprawa komputerów/laptopów")) {
                                        subcategory = "naprawa_komputerow_laptopow";
                                    } else if (item.equals("malowanie")) {
                                        subcategory = "malowanie";
                                    } else if (item.equals("tapetowanie")) {
                                        subcategory = "tapetowanie";
                                    } else if (item.equals("kładzenie kafelek")) {
                                        subcategory = "kladzenie_kafelek";
                                    } else if (item.equals("kładzenie paneli podłogowych")) {
                                        subcategory = "kladzenie_paneli_podlogowych";
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        //singleuser.putExtra("user_description", description);

                        startActivity(singleuser);

                        Bundle bundle = new Bundle();
                        bundle.putString("user_number", number);
                        FragmentSubPage1 fragobj = new FragmentSubPage1();
                        fragobj.setArguments(bundle);

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id_position", id_position);
                        bundle1.putString("user_id",user_id);
                        FragmentSubPage2 objSubPage2 = new FragmentSubPage2();
                        objSubPage2.setArguments(bundle1);

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

