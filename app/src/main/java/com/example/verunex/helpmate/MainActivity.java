package com.example.verunex.helpmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private NavigationView mNavigationView;
    private DatabaseReference mDatabaseReference;
    private TextView mName;
    private ImageView mImageView;
    String id_cur = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            id_cur = "null";
        }else {
            id_cur = mFirebaseAuth.getCurrentUser().getUid();

        }
        Log.v ("Id_MainActivity", id_cur);

        if (!id_cur.equals("null")){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            mNavigationView = (NavigationView) findViewById(R.id.nav_view);
            mNavigationView.setNavigationItemSelectedListener(this);

            View header = mNavigationView.getHeaderView(0);

            mName = (TextView)header.findViewById(R.id.nav_name);
            mImageView = (ImageView)header.findViewById(R.id.imageuser);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

        }else{
            String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();

                    mName.setText(name);

                    String user_image_uri = dataSnapshot.child("user_image").getValue(String.class);
                    if(user_image_uri.isEmpty()){

                    }else{
                        Picasso.with(getBaseContext()).load(user_image_uri).into(mImageView);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void initControl (){
        ImageButton bHydraulika = (ImageButton) findViewById(R.id.hydraulika);
        ImageButton bElektryka = (ImageButton) findViewById(R.id.elektryka);
        ImageButton bNaprawaUrzadzen = (ImageButton) findViewById(R.id.naprawaUrzadzen);
        ImageButton bPomocDomowa = (ImageButton) findViewById(R.id.pomocDomowa);
        ImageButton bRemonty = (ImageButton) findViewById(R.id.remonty);
        ImageButton bPomocNaukowa = (ImageButton) findViewById(R.id.pomocNaukowa);
        ImageButton bOpieka = (ImageButton) findViewById(R.id.opieka);
        ImageButton bInne = (ImageButton) findViewById(R.id.inne);
        ImageButton bOgrodnictwo = (ImageButton)findViewById(R.id.ogrodnictwo);

        bHydraulika.setOnClickListener(this);
        bOgrodnictwo.setOnClickListener(this);
        bElektryka.setOnClickListener(this);
        bNaprawaUrzadzen.setOnClickListener(this);
        bPomocDomowa.setOnClickListener(this);
        bRemonty.setOnClickListener(this);
        bPomocNaukowa.setOnClickListener(this);
        bOpieka.setOnClickListener(this);
        bInne.setOnClickListener(this);
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
            Intent i = new Intent(this, UserProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.user_favorite) {
            Intent i = new Intent(this, UserFavouriteActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(this, CategoryList.class);
        //i.putExtra("Id_key", id_cur);

        switch (v.getId()) {
            case R.id.hydraulika:
                i.putExtra("Selected", "Hydraulika");
                startActivity(i);
                break;
            case R.id.elektryka:
                i.putExtra("Selected", "Elektryka");
                startActivity(i);
                break;
            case R.id.opieka:
                i.putExtra("Selected", "Opieka");
                startActivity(i);
                break;
            case R.id.pomocDomowa:
                i.putExtra("Selected", "Pomoc_domowa");
                startActivity(i);
                break;
            case R.id.ogrodnictwo:
                i.putExtra("Selected", "Ogrodnictwo");
                startActivity(i);
                break;
            case R.id.remonty:
                i.putExtra("Selected", "Remonty");
                startActivity(i);
                break;
            case R.id.pomocNaukowa:
                i.putExtra("Selected", "Pomoc_naukowa");
                startActivity(i);
                break;
            case R.id.naprawaUrzadzen:
                i.putExtra("Selected", "Naprawa_urzadzen");
                startActivity(i);
                break;
            case R.id.inne:
                i.putExtra("Selected", "Inne");
                startActivity(i);
                break;
        }
    }
}
