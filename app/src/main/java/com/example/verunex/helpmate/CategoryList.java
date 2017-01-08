package com.example.verunex.helpmate;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.internal.zzoe;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CategoryList extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mUsersList;
    private DatabaseReference mDatabaseReference;
    private NavigationView mNavigationView;
    private TextView mName;
    private ImageView mImageView;
    private DatabaseReference checkserviceuser;

    private DatabaseReference liketest;
    private Query mQuery;

    private DatabaseReference OldDataChange;


    private Spinner mSpinner;

    private FirebaseAuth mFirebaseAuth;
    String cureent_user_id;
    String choice = "false";

    private DatabaseReference getSubcategories;

    private String email;
    private String image;

    Double myLastA=0.0;
    Double myLastB=0.0;

    String temp;
    String temp2;

    private RequestQueue mRequestQueue;

    String myAddress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_category);

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key;



        String category = getIntent().getStringExtra("Category");
        String subcategory = getIntent().getStringExtra("Subcategory");
        myLastA = Double.valueOf(getIntent().getStringExtra("myLastA"));
        myLastB = Double.valueOf(getIntent().getStringExtra("myLastB"));

        temp =getIntent().getStringExtra("myLastA");
        temp2=getIntent().getStringExtra("myLastB");

        Log.v("myLastA ", String.valueOf(myLastA));
        Log.v("myLastB ", String.valueOf(myLastB));

        //cureent_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Log.v ("Id_KeyCategory", cureent_user_id);
        //cureent_user_id = getIntent().getStringExtra("Id_key");
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            cureent_user_id = "null";
            id_key = "null";
        } else {
            cureent_user_id = mFirebaseAuth.getCurrentUser().getUid();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(cureent_user_id);


            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();

                    mName.setText(name);

                    String user_image_uri = dataSnapshot.child("user_image").getValue(String.class);
                    if (user_image_uri.isEmpty()) {

                    } else {
                        Picasso.with(getBaseContext()).load(user_image_uri).transform(new Circle()).into(mImageView);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        if (!cureent_user_id.equals("null")) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            mNavigationView = (NavigationView) findViewById(R.id.nav_view);

            checkserviceuser = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(cureent_user_id);

            checkserviceuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choice = dataSnapshot.child("service_state").getValue().toString();

                    Log.v("Wartosc choice ", choice);

                    if (choice.equals("true")) {
                        Log.v("Tu moze ", "moze");
                        mNavigationView.getMenu().findItem(R.id.nav_userServices).setVisible(false);
                        mNavigationView.getMenu().findItem(R.id.group_item_profile_services).setVisible(true);
                    } else {
                        mNavigationView.getMenu().findItem(R.id.nav_userServices).setVisible(true);
                        mNavigationView.getMenu().findItem(R.id.group_item_profile_services).setVisible(false);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mNavigationView.setNavigationItemSelectedListener(this);

            View header = mNavigationView.getHeaderView(0);

            mName = (TextView) header.findViewById(R.id.nav_name);
            mImageView = (ImageView) header.findViewById(R.id.imageuser);
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        }

        mSpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_simple_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(this);


        Log.v("Kategoria ", category);
        Log.v("Sybkategori ", subcategory);


        String label = "";
        if (subcategory.equals("naprawa_wyciekow")) {
            label = "Naprawa wycieków";
        } else if (subcategory.equals("wymiana_armatury")) {
            label = "Wymiana armatury";
        } else if (subcategory.equals("instalacje_elektryczne")) {
            label = "Instalacje elektryczne";
        } else if (subcategory.equals("naprawa_awaryjna")) {
            label = "Naprawa awaryjna";
        } else if (subcategory.equals("sprzatanie")) {
            label = "Sprzątanie";
        } else if (subcategory.equals("prasowanie")) {
            label = "Prasowanie";
        } else if (subcategory.equals("mycie_okien")) {
            label = "Mycie okien";
        } else if (subcategory.equals("opieka_do_dzieci")) {
            label = "Opieka do dzieci";
        } else if (subcategory.equals("opieka_do_osob_starszych")) {
            label = "Opieka do osób starszych";
        } else if (subcategory.equals("opieka_dzieci_i_osob_niepelnosprawnych")) {
            label = "Opieka dzieci i osób niepełnosprawnych";
        } else if (subcategory.equals("wyprowadzanie_zwierzat")) {
            label = "Wyprowadzanie zwierząt";
        } else if (subcategory.equals("korepetycje")) {
            label = "Korepetycje";
        } else if (subcategory.equals("koszenie_trawy")) {
            label = "Koszenie trawy";
        } else if (subcategory.equals("prace_porzadkowe")) {
            label = "Prace porządkowe";
        } else if (subcategory.equals("pielegnacja_ogrodu")) {
            label = "Pielęgnacja ogrodu";
        } else if (subcategory.equals("naprawa_drobnego_AGD")) {
            label = "Naprawa drobnego AGD";
        } else if (subcategory.equals("naprawa_AGD")) {
            label = "Naprawa AGD";
        } else if (subcategory.equals("naprawa_RTV")) {
            label = "Naprawa RTV";
        } else if (subcategory.equals("naprawa_komputerow_laptopow")) {
            label = "Naprawa komputerów/laptopów";
        } else if (subcategory.equals("malowanie")) {
            label = "Malowanie";
        } else if (subcategory.equals("tapetowanie")) {
            label = "Tapetowanie";
        } else if (subcategory.equals("kladzenie_kafelek")) {
            label = "Kładzenie kafelek";
        } else if (subcategory.equals("kladzenie_paneli_podlogowych")) {
            label = "Kładzenie paneli podłogowych";
        }

        setTitle(label);


        //Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Offers").child(category).child(subcategory);
        mQuery = mDatabaseReference.orderByChild("rate").startAt("5.0f").endAt("1.0f");


        //List
        mUsersList = (RecyclerView) findViewById(R.id.users_row);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.

        client.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.user_profile) {
            Intent i = new Intent(this, EditUserProfile.class);
            startActivity(i);

        } else if (id == R.id.user_favorite) {
            Intent i = new Intent(this, UserFavouriteActivity.class);
            if(temp.equals("0.0") && myLastB.equals("0.0")){
                i.putExtra("myLastA", "0.0");
                i.putExtra("myLastB", "0.0");
            }else{
                i.putExtra("myLastA", temp);
                i.putExtra("myLastB", temp2);
            }
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.nav_userServices) {
            Intent intent = new Intent(getApplicationContext(), ServiceProviderPop.class);
            startActivity(intent);
        } else if (id == R.id.nav_userProfileServices) {
            Intent intent = new Intent(getApplicationContext(), ServiceUserProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_userComment) {
            Intent intent = new Intent(getApplicationContext(), MyCommentServiceUser.class);
            startActivity(intent);
        } else if (id == R.id.user_settings) {
            Intent intent = new Intent(getApplicationContext(), UserSettings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
        String choice = mSpinner.getSelectedItem().toString();

        Log.v("Choice", choice);
        if (choice.equals("Ocena")) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            mUsersList.setLayoutManager(layoutManager);

            choice = "rate";
            mQuery = mDatabaseReference.orderByChild("rate");
        } else if (choice.equals("Nazwa")) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setReverseLayout(false);
            layoutManager.setStackFromEnd(false);
            mUsersList.setLayoutManager(layoutManager);

            choice = "name";
            mQuery = mDatabaseReference.orderByChild(choice);
        }

        FirebaseRecyclerAdapter<User2, User2ProfileViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User2, User2ProfileViewHolder>(
                User2.class,
                R.layout.users_list,
                User2ProfileViewHolder.class,
                mQuery
        ) {
            @Override
            protected void populateViewHolder(final User2ProfileViewHolder viewHolder, final User2 model, final int position) {
                final String category = model.getCategory();

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
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
                }

                String addressTest = model.getAddress();
                Log.v("address value", addressTest);

                List<android.location.Address> addressList = null;

                Float distance = null;
                if (addressTest != null || addressTest != "") {

                    Geocoder geocoder = new Geocoder(getBaseContext());
                    try {
                        addressList = geocoder.getFromLocationName(addressTest, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null || addressList.size()==0) {
                        android.location.Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        LatLng latLngMy = new LatLng(myLastA, myLastB);


                        Location loc1 = new Location("Marker");
                        loc1.setLatitude(address.getLatitude());
                        loc1.setLongitude(address.getLongitude());

                        if(myLastA.equals(0.0) && myLastB.equals(0.0)){

                            viewHolder.distance.setVisibility(View.INVISIBLE);

                        }else{
                            Location currentLoc = new Location("Current");
                            currentLoc.setLatitude(myLastA);
                            currentLoc.setLongitude(myLastB);

                            distance = currentLoc.distanceTo(loc1);


                            String filterDistance = String.valueOf(distance/1000);
                            //filterDistance = filterDistance.substring(0, filterDistance.length()-4);

                            Double toBeTruncated = new Double(distance/1000);

                            Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .doubleValue();

                            String readyDistance = truncatedDouble +" km od Ciebie";

                            viewHolder.distance.setText(readyDistance);
                        }

                        Log.v("Distance", String.valueOf(distance));
                    }


                }


                viewHolder.setAddress(model.getAddress());
                viewHolder.setName(model.getName());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setNumber(model.getNumber());
                //viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setImage(getApplicationContext(), model.getUser_image());
                viewHolder.setRate(model.getRate());
                //viewHolder.setUser_id(model.getUser_id());
                viewHolder.setUser_id(model.getUser_id());
                viewHolder.setEmail(model.getEmail());
                //viewHolder.setDescription(model.getDescription());




                final String subcategory = model.getSubcategory();
                final String service_state = model.getService_state();
                //final String email = model.getEmail();
                //Log.v ("email", email);
                final String number = model.getNumber();
                Log.v("number", number);
                final String name = model.getName();
                //final String category = model.getCategory();
                final String image = model.getUser_image();
                final String rate = model.getRate();
                final String desc = model.getDesc();
                final String user_id = model.getUser_id();
                final String address = model.getAddress();


                final String id_position = getRef(position).getKey();
                Log.v("Pozycja id_position", id_position);

                final String key = getRef(position).getKey();

                liketest = FirebaseDatabase.getInstance().getReference().child("UserFavourite");

                liketest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(cureent_user_id).hasChild(model.getUser_id())) {
                            viewHolder.favouriteBox.setChecked(true);
                            viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_like);
                        } else {
                            viewHolder.favouriteBox.setChecked(false);
                            viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_unlike);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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

                        getSubcategories = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_position).child("categories");
                        getSubcategories.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (int i = 1; i <= 23; i++) {
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

                        /*
                         Bundle bundle = new Bundle();
                        bundle.putString("number", number);
                        bundle.putString("desc", desc );
                        bundle.putString("email", email);
                        FragmentSubPage1 fragobj = new FragmentSubPage1();
                        fragobj.setArguments(bundle);
                         */


                        Bundle bundle2 = new Bundle();
                        //bundle.putString("Id_key", cureent_user_id);
                        FragmentSubPage1 fragobj2 = new FragmentSubPage1();
                        fragobj2.setArguments(bundle2);



                        /*Bundle bundle1 = new Bundle();
                        bundle1.putString("id_position", id_position);
                        FragmentSubPage2 objSubPage2 = new FragmentSubPage2();
                        objSubPage2.setArguments(bundle1);
*/
                    }
                });

                if (number.equals("")) {
                    viewHolder.btn.setVisibility(View.INVISIBLE);
                    viewHolder.btn2.setVisibility(View.INVISIBLE);
                }

                viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                        smsIntent.putExtra("sms_body", "Witam pisze z serwisu HelpMate!\n");
                        startActivity(smsIntent);
                    }
                });

                viewHolder.btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Zapytanie HelpMate");
                        intent.putExtra(Intent.EXTRA_TEXT, "Witam pisze z portalu HelpMate");
                        startActivity(Intent.createChooser(intent, ""));
                    }
                });


                if (!cureent_user_id.equals("null")) {

                    viewHolder.favouriteBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //final String user_from_list_id = model.getUid();
                            final String key = getRef(position).getKey();


                            String model1 = model.getUser_id();

                            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserFavourite").child(cureent_user_id).child(model1);

                            Log.v("key: ", key);

                            final DatabaseReference favourite = mDatabaseReference;

                            if (viewHolder.favouriteBox.isChecked() == true) {
                                viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_like);
                                favourite.child("category").setValue(category);
                                favourite.child("user_image").setValue(image);
                                favourite.child("rate").setValue(rate);
                                favourite.child("email").setValue(email);
                                favourite.child("name").setValue(name);
                                favourite.child("number").setValue(number);
                                favourite.child("user_id").setValue(user_id);
                                favourite.child("address").setValue(address);
                                favourite.child("service_state").setValue(service_state);
                                favourite.child("desc").setValue(desc);
                                favourite.child("subcategory").setValue(subcategory);
                                Toast.makeText(getBaseContext(), "Dodano do ulubionych!", Toast.LENGTH_SHORT).show();
                            } else {
                                viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_unlike);
                                favourite.removeValue();
                                Toast.makeText(getBaseContext(), "Usunięto z ulubionych!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    viewHolder.favouriteBox.setVisibility(View.INVISIBLE);
                }
            }
        };
        mUsersList.setAdapter(mFirebaseRecyclerAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CategoryList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
