package com.example.verunex.helpmate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mUsersList;
    private DatabaseReference mDatabaseReference;
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, UserProfileViewHolder> mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UserProfileViewHolder>(
                User.class,
                R.layout.users_list,
                UserProfileViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final UserProfileViewHolder viewHolder, final User model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setCategory(model.getCategory());
                viewHolder.setNumber(model.getNumber());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setRate(model.getRate());
                viewHolder.setUser_id(model.getUser_id());
                //viewHolder.setDescription(model.getDescription());

                final String number = model.getNumber();
                final String name = model.getName();
                final String category = model.getCategory();
                final String image = model.getImage();
                final String rate = model.getRate();
                //final String description = viewHolder.setDescription(model.getDescription());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleuser = new Intent(getBaseContext(), SingleUserActivity.class);
                        singleuser.putExtra("user_name", name);
                        singleuser.putExtra("user_category", category);
                        singleuser.putExtra("user_image", image);
                        singleuser.putExtra("user_rate", rate);
                        //singleuser.putExtra("user_description", description);
                        startActivity(singleuser);

                        Bundle bundle = new Bundle();
                        bundle.putString("user_number", number);
                        FragmentSubPage1 fragobj = new FragmentSubPage1();
                        fragobj.setArguments(bundle);

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
                viewHolder.favoriteBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String user_from_list_id = model.getUser_id();

                        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UsersTest").child(cureent_user_id);

                        final DatabaseReference favorite = mDatabaseReference.child("favorite");



                        if(viewHolder.favoriteBox.isChecked()==true) {
                            favorite.child(user_from_list_id).setValue(user_from_list_id);
                            Toast.makeText(getBaseContext(), "Dodano do ulubionych!", Toast.LENGTH_SHORT).show();
                        }else{
                            favorite.child(user_from_list_id).removeValue();
                            Toast.makeText(getBaseContext(), "Usunieto z ulubionych!", Toast.LENGTH_SHORT).show();
                        }

                        /*favorite.child(user_from_list_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Toast.makeText(getBaseContext(), "Juz jest w ulubionych!", Toast.LENGTH_SHORT).show();
                                    favorite.child(user_from_list_id).removeValue();
                                }
                                else{
                                    favorite.child(user_from_list_id).setValue(user_from_list_id);
                                    Toast.makeText(getBaseContext(),"Dodano do ulubionych!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

                        //viewHolder.favoriteBox.setVisibility(View.INVISIBLE);



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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
