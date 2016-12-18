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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(this, CategoryList.class);

        switch (v.getId()) {
            case R.id.hydraulika:
                i.putExtra("Selected", "Hydraulik");
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
                i.putExtra("Selected", "Pomoc domowa");
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
                i.putExtra("Selected", "Pomoc naukowa");
                startActivity(i);
                break;
            case R.id.naprawaUrzadzen:
                i.putExtra("Selected", "Naprawa urządzeń");
                startActivity(i);
                break;
            case R.id.inne:
                i.putExtra("Selected", "Inne");
                startActivity(i);
                break;
        }
    }
}
