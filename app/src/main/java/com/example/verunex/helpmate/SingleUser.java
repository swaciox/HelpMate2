package com.example.verunex.helpmate;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleUser extends AppCompatActivity {

    private TextView mUserName, mUserCategory, mUserNumber;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        String user_name = getIntent().getExtras().getString("user_name");
        String user_category =getIntent().getExtras().getString("user_category");
        String user_number = getIntent().getExtras().getString("user_number");
        String image = getIntent().getExtras().getString("user_image");

        mUserName = (TextView)findViewById(R.id.user_name);
        //mUserNumber = (TextView)findViewById(R.id.user_number);
        mUserCategory = (TextView)findViewById(R.id.user_category);
        mImageView = (ImageView)findViewById(R.id.user_image);

        Picasso.with(getBaseContext()).load(image).into(mImageView);
        mUserName.setText(user_name);
        mUserCategory.setText(user_category);

        //mUserNumber.setText(user_number);


    }
}
