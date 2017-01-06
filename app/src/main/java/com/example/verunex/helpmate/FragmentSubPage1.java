package com.example.verunex.helpmate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentSubPage1 extends Fragment{

    private TextView desc, number, numberSms, email, userCategories;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sub_page1, container, false);

        desc = (TextView)view.findViewById(R.id.userDesc);
        number = (TextView)view.findViewById(R.id.number);
        numberSms = (TextView)view.findViewById(R.id.numberSms);
        email = (TextView)view.findViewById(R.id.userEmail);
        userCategories = (TextView)view.findViewById(R.id.userCategories);

       // Bundle bundle = this.getArguments();

        if(this.getArguments()!=null){
            String numberString = this.getArguments().getString("number");
            String emailString = this.getArguments().getString("email");
            String descString = this.getArguments().getString("desc");
            String subcategory = this.getArguments().getString("subcategory");

            desc.setText(descString);
            number.setText(numberString);
            numberSms.setText(numberString);
            email.setText(emailString);
            userCategories.setText(subcategory);


        }



        return view;
    }



}
