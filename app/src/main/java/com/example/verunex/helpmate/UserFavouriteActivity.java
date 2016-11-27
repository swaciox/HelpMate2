package com.example.verunex.helpmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFavouriteActivity extends AppCompatActivity{

    private ListView mListView;

    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favourite);

        mListView = (ListView)findViewById(R.id.testlist);

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserFavourite").child(id_key).child("favourite");



        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                mDatabaseReference

        ) {

            @Override
            protected void populateView(View v, String model, int position) {


                    final TextView textView = (TextView) v.findViewById(android.R.id.text1);
                    textView.setText(model);


                //final String s = textView.getText().toString().trim();

                /*v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent (getBaseContext(), sialala.class);
                        i.putExtra("s",s);
                        startActivity(i);
                    }
                });*/
            }
        };

        mListView.setAdapter(firebaseListAdapter);
    }
}
