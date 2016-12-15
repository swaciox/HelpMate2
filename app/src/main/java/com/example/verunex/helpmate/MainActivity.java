package com.example.verunex.helpmate;

import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mUsersList;
    private DatabaseReference mDatabaseReference;

    private DatabaseReference liketest;
    private Query mQuery;

    private FirebaseAuth mFirebaseAuth;
    String cureent_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        mFirebaseAuth = FirebaseAuth.getInstance();
        cureent_user_id = mFirebaseAuth.getCurrentUser().getUid();

        //Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mQuery = mDatabaseReference.orderByChild("category").equalTo("Opieka do dzieci");
        //List
        mUsersList = (RecyclerView) findViewById(R.id.users_row);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(getBaseContext()));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseRecyclerAdapter<User, UserProfileViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserProfileViewHolder>(
                User.class,
                R.layout.users_list,
                UserProfileViewHolder.class,
                mQuery
        ) {
            @Override
            protected void populateViewHolder(final UserProfileViewHolder viewHolder, final User model, final int position) {
                final String category = model.getCategory();


                    viewHolder.setName(model.getName());
                    viewHolder.setCategory(model.getCategory());
                    viewHolder.setNumber(model.getNumber());
                    viewHolder.setImage(getApplicationContext(), model.getImage());
                    viewHolder.setRate(model.getRate());
                    viewHolder.setUser_id(model.getUser_id());
                    //viewHolder.setDescription(model.getDescription());



                final String number = model.getNumber();
                final String name = model.getName();
                //final String category = model.getCategory();
                final String image = model.getImage();
                final String rate = model.getRate();
                //final String description = viewHolder.setDescription(model.getDescription());
                final String user_id = model.getUser_id();

                //sprawdzenie czy dany uzytkownik juz lubi

                //like czy nie lista tablica

                final String id_position = getRef(position).getKey();
                Log.v ("Pozycja id_position", id_position);

                final String key = getRef(position).getKey();

                liketest = FirebaseDatabase.getInstance().getReference().child("UserFavourite");

                liketest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(cureent_user_id).hasChild(key)){
                            viewHolder.favouriteBox.setChecked(true);
                            viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_like);
                        }else{
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
                viewHolder.favouriteBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String user_from_list_id = model.getUser_id();
                        final String key = getRef(position).getKey();

                        String model1 = model.getUser_id();

                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserFavourite").child(cureent_user_id);

                        final DatabaseReference favourite = mDatabaseReference;

                        if(viewHolder.favouriteBox.isChecked()==true) {
                            viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_like);
                            favourite.child(key).setValue(key);
                            Toast.makeText(getBaseContext(), "Dodano do ulubionych!", Toast.LENGTH_SHORT).show();
                        }else{
                            viewHolder.favouriteBox.setButtonDrawable(R.drawable.ic_unlike);
                            favourite.child(key).removeValue();
                            Toast.makeText(getBaseContext(), "Usunięto z ulubionych!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        };
        mUsersList.setAdapter(mFirebaseRecyclerAdapter);
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
}
