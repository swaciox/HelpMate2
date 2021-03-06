package com.example.verunex.helpmate;

import android.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private NavigationView mNavigationView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference checkserviceuser;
    private TextView mName;
    private ImageView mImageView;
    String id_cur = "";
    String choice = "false";
    String useraftersession;
    private FirebaseUser mFirebaseUser;
    private RequestQueue mRequestQueue;
    String myLastKnowLocation;
    String myLastA="0.0";
    String myLastB="0.0";

    private BroadcastReceiver broadcastReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            id_cur = "null";
        } else {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            id_cur = mFirebaseAuth.getCurrentUser().getUid();
        }
        Log.v("Id_MainActivity", id_cur);

        lastlocation();

        //getLocation();


        String checkS = choice;
        Log.v("Wartosc check ", checkS);

        if (!id_cur.equals("null")) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            mNavigationView = (NavigationView) findViewById(R.id.nav_view);

            checkserviceuser = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_cur);

            checkserviceuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choice = dataSnapshot.child("service_state").getValue().toString();

                    Log.v("Wartosc choice ", choice);

                    if (choice.equals("true")) {
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

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

        } else {
            String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

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

    }

   /* public LatLng getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            Log.v("Checka ", "a "+lat+" b"+lon);
            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }*/

    private void lastlocation() {


        //if(!runtime_permissions()){
            Intent i =new Intent(getApplicationContext(),GPS_Service.class);
            startService(i);
        //}


    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            } else {
                runtime_permissions();
            }
        }
    }


        @Override
    protected void onStart() {
        super.onStart();

       /* checkserviceuser = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_cur);

        checkserviceuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                choice = (String) dataSnapshot.child("service_state").getValue().toString();

                Log.v ("Wartosc choice ", choice);

                if(choice.equals("true")){
                    Log.v("Bylem tu ", "ol yaaa");
                    mNavigationView.getMenu().findItem(R.id.nav_userServices).setVisible(false);
                    mNavigationView.getMenu().findItem(R.id.group_item_profile_services).setVisible(true);
                }else{
                    mNavigationView.getMenu().findItem(R.id.nav_userServices).setVisible(true);
                    mNavigationView.getMenu().findItem(R.id.group_item_profile_services).setVisible(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mNavigationView.setNavigationItemSelectedListener(this);
*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String a="" +intent.getExtras().get("b");
                    String b = ""+intent.getExtras().get("a");
                    Log.v("Location", a+","+b);
                    myLastA = a;
                    myLastB = b;


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));


    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent i = new Intent(getApplicationContext(),GPS_Service.class);
        stopService(i);

    }

    public void initControl (){
        ImageButton bHydraulika = (ImageButton) findViewById(R.id.hydraulika);
        ImageButton bElektryka = (ImageButton) findViewById(R.id.elektryka);
        ImageButton bNaprawaUrzadzen = (ImageButton) findViewById(R.id.naprawaUrzadzen);
        ImageButton bPomocDomowa = (ImageButton) findViewById(R.id.pomocDomowa);
        ImageButton bRemonty = (ImageButton) findViewById(R.id.remonty);
        ImageButton bPomocNaukowa = (ImageButton) findViewById(R.id.pomocNaukowa);
        ImageButton bOpieka = (ImageButton) findViewById(R.id.opieka);
        ImageButton bOgrodnictwo = (ImageButton)findViewById(R.id.ogrodnictwo);

        bHydraulika.setOnClickListener(this);
        bOgrodnictwo.setOnClickListener(this);
        bElektryka.setOnClickListener(this);
        bNaprawaUrzadzen.setOnClickListener(this);
        bPomocDomowa.setOnClickListener(this);
        bRemonty.setOnClickListener(this);
        bPomocNaukowa.setOnClickListener(this);
        bOpieka.setOnClickListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                Intent i = new Intent(this, BackPressPop.class);
                i.putExtra("temp", 0);
                startActivity(i);
            }else{
                Intent i = new Intent(this, BackPressPop.class);
                i.putExtra("temp", 1);
                startActivity(i);
            }

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
            if(!myLastA.equals("0.0")&& !myLastB.equals("0.0")){
                i.putExtra("myLastA", myLastA);
                i.putExtra("myLastB", myLastB);
            }else{
                i.putExtra("myLastA", "0.0");
                i.putExtra("myLastB", "0.0");
            }
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_userServices){
            Intent intent = new Intent(getApplicationContext(), ServiceProviderPop.class);
            startActivity(intent);
        } else if (id == R.id.nav_userProfileServices){
            Intent intent = new Intent(getApplicationContext(), ServiceUserProfile.class);
            startActivity(intent);
        }else if (id == R.id.nav_userComment){
            Intent intent = new Intent(getApplicationContext(), MyCommentServiceUser.class);
            startActivity(intent);
        }else if (id == R.id.user_settings){
            Intent intent = new Intent(getApplicationContext(), UserSettings.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(this, SubCategoryPop.class);
        if(!myLastA.equals("")&& !myLastB.equals("")){
            i.putExtra("myLastA", myLastA);
            i.putExtra("myLastB", myLastB);
        }else{
            i.putExtra("myLastA", "0.0");
            i.putExtra("myLastB", "0.0");
        }


        switch (v.getId()) {
            case R.id.hydraulika:
                i.putExtra("Selected", "hydraulika");
                startActivity(i);
                break;
            case R.id.elektryka:
                i.putExtra("Selected", "elektryka");
                startActivity(i);
                break;
            case R.id.opieka:
                i.putExtra("Selected", "opieka");
                startActivity(i);
                break;
            case R.id.pomocDomowa:
                i.putExtra("Selected", "pomoc_domowa");
                startActivity(i);
                break;
            case R.id.ogrodnictwo:
                i.putExtra("Selected", "ogrodnictwo");
                startActivity(i);
                break;
            case R.id.remonty:
                i.putExtra("Selected", "remonty");
                startActivity(i);
                break;
            case R.id.pomocNaukowa:
                i.putExtra("Selected", "pomoc_naukowa");
                startActivity(i);
                break;
            case R.id.naprawaUrzadzen:
                i.putExtra("Selected", "naprawa_urzadzen");
                startActivity(i);
                break;
        }
    }
}
