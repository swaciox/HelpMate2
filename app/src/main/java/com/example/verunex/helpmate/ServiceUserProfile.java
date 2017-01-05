package com.example.verunex.helpmate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceUserProfile extends AppCompatActivity {

    private ImageView userImage;
    private TextView userName, userCategory;
    private RatingBar user_rate;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    //Categories choice
    private TextView userSubCategories;
    private Button editSubCategories;

    //contact
    private TextView userNumber, userNumberCell, userEmail;
    private Button editContact;

    //description
    private TextView userDesc;
    private Button userDescChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_services);

        mFirebaseAuth = FirebaseAuth.getInstance();

        String id_key = mFirebaseAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id_key);

        initControl();

        userSubCategoriesChoice();

        contactEdit();

        descEdit();


    }

    @Override
    protected void onStart() {
        super.onStart();

        userOldData();
    }

    private void descEdit() {
        userDescChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), DescriptionPopUp.class);
                startActivity(i);
            }
        });
    }

    private void contactEdit() {

        editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ContactPop.class);
                startActivity(i);
            }
        });


    }

    private void initControl(){

        //userOldData
        userImage = (ImageView) findViewById(R.id.user_image);
        userName = (TextView) findViewById(R.id.user_name);
        userCategory = (TextView) findViewById(R.id.user_category);

        //categories
        userSubCategories = (TextView)findViewById(R.id.userCategories);
        editSubCategories = (Button)findViewById(R.id.EditCategories);

        //contact
        userNumber = (TextView)findViewById(R.id.number);
        userNumberCell = (TextView)findViewById(R.id.numberSms);
        userEmail = (TextView)findViewById(R.id.userEmail);
        editContact = (Button)findViewById(R.id.editContactB);

        //description
        userDesc = (TextView)findViewById(R.id.userDesc);
        userDescChange = (Button)findViewById(R.id.editUserDesc);

    }

    private void userOldData(){

        DatabaseReference userData = mDatabaseReference;

        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                userName.setText(name);

                String user_image_uri = dataSnapshot.child("user_image").getValue().toString();
                if(user_image_uri.isEmpty()){
                    userImage.setImageResource(R.drawable.person);
                }else{
                    Log.v ("user_image uri",user_image_uri);
                    Picasso.with(getBaseContext()).load(user_image_uri).transform(new Circle()).into(userImage);
                }

                //contact
                String number = dataSnapshot.child("number").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();

                userNumberCell.setText(number);
                userNumber.setText(number);
                userEmail.setText(email);

                //desc
                String desc = dataSnapshot.child("desc").getValue().toString();
                userDesc.setText(desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void userSubCategoriesChoice()
    {
        editSubCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CategoriesChoice.class);
                startActivity(i);
            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String temp;
                String temp2;
                String desc;
                String categories = "";

                List<String> category = new ArrayList<String>();
                int position = 0;

                for (int i = 1; i <=23; i++){
                    temp = "sub"+i;

                    temp2 = dataSnapshot.child("categories").child(temp).child("state").getValue().toString();
                    if(temp2.equals("true")){
                        desc = dataSnapshot.child("categories").child(temp).child("description").getValue().toString();
                        categories = categories + "-" + desc+"\n";

                        if(temp.equals("sub1") || temp.equals("sub2")){
                            category.add("Hydraulika");
                        }else if (temp.equals("sub3") || temp.equals("sub4")){
                            category.add("Elektryka");
                        }else if (temp.equals("sub5") || temp.equals("sub6") || temp.equals("sub7")){
                            category.add("Pomoc domowa");
                        } else if(temp.equals("sub8")|| temp.equals("sub9")|| temp.equals("sub10")|| temp.equals("sub11")){
                            category.add("Opieka");
                        } else if (temp.equals("sub12")){
                            category.add("Korepetycje");
                        } else if (temp.equals("sub13")|| temp.equals("sub14")|| temp.equals("sub15")){
                            category.add("Ogrodnictwo");
                        } else if (temp.equals("sub16")|| temp.equals("sub17")|| temp.equals("sub18")|| temp.equals("sub19")){
                            category.add("Naprawa urządzeń");
                        } else if (temp.equals("sub20")|| temp.equals("sub21")|| temp.equals("sub22")|| temp.equals("sub23")){
                            category.add("Remonty");
                        }
                    }
                }

                List<String> finallist = new ArrayList<String>();

                //usuniecie duplikatow
                Set<String> mySet = new HashSet<String>(category);
                for(String tempList : mySet ){
                    Log.v ("templist", tempList);
                    finallist.add(tempList);
                }

                String filterCategory = "";
                for(int i = 0; i < finallist.size(); i++){
                    filterCategory = filterCategory +finallist.get(i)+ ", ";
                    Log.v("filter ", filterCategory);
                }


                userCategory.setText(deleteLastChar(filterCategory));
                userSubCategories.setText(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String deleteLastChar(String s ){

        String temp = "";
        if (!s.isEmpty()){
            temp = s.substring(0,s.length()-2);
        }
        return temp;
    }
}
