package com.example.verunex.helpmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleUser extends AppCompatActivity {

    private TextView mUserName, mUserCategory, mUserNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        String user_name = getIntent().getExtras().getString("user_name");
        String user_category =getIntent().getExtras().getString("user_category");
        String user_number = getIntent().getExtras().getString("user_number");

        mUserName = (TextView)findViewById(R.id.user_name);
        mUserNumber = (TextView)findViewById(R.id.user_number);
        mUserCategory = (TextView)findViewById(R.id.user_category);

        mUserName.setText(user_name);
        mUserCategory.setText(user_category);
        mUserNumber.setText(user_number);


    }
}
