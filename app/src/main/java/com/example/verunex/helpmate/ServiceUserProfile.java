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

    //addToDatabase
    private Button addToDatabase;
    private DatabaseReference addUserToDatabaseReference;

    //
    String filterCategory = "";


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

        addUserToDatabase();


    }

    private void addUserToDatabase() {

        addUserToDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Offers");

        addToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //data
                        String name = dataSnapshot.child("name").getValue().toString();
                        String number = dataSnapshot.child("number").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String desc = dataSnapshot.child("desc").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String image = dataSnapshot.child("user_image").getValue().toString();
                        String uid = dataSnapshot.child("uid").getValue().toString();
                        String service_state = dataSnapshot.child("service_state").getValue().toString();

                        String tempCategory = "";
                        for(int i =1; i <=23; i++){
                            String temp = "sub" + i ;

                            String item = dataSnapshot.child("categories").child(temp).child("description").getValue().toString();

                            String subcategory="";
                            if (item.equals("naprawa wycieków")){
                                subcategory = "naprawa_wyciekow";
                            } else if (item.equals("wymiana armatury")) {
                                subcategory = "wymiana_armatury";
                            } else if (item.equals("instalacje elektryczne")){
                                subcategory = "instalacje_elektryczne";
                            } else if (item.equals("naprawa awaryjna")){
                                subcategory = "naprawa_awaryjna";
                            } else if (item.equals("sprzątanie")){
                                subcategory = "sprzatanie";
                            } else if (item.equals("prasowanie")){
                                subcategory = "prasowanie";
                            } else if (item.equals("mycie okien")){
                                subcategory = "mycie_okien";
                            } else if (item.equals("opieka do dzieci")) {
                                subcategory = "opieka_do_dzieci";
                            } else if (item.equals("opieka do osób starszych")) {
                                subcategory = "opieka_do_osob_starszych";
                            } else if (item.equals("opieka dzieci i osób niepełnosprawnych")) {
                                subcategory = "opieka_dzieci_i_osob_niepelnosprawnych";
                            } else if (item.equals("wyprowadzanie zwierząt")) {
                                subcategory = "wyprowadzanie_zwierzat";
                            } else if (item.equals("korepetycje")) {
                                subcategory = "korepetycje";
                            } else if (item.equals("koszenie trawy")) {
                                subcategory = "koszenie_trawy";
                            } else if (item.equals("prace porządkowe")) {
                                subcategory = "prace_porzadkowe";
                            } else if (item.equals("pielęgnacja ogrodu")) {
                                subcategory = "pielegnacja_ogrodu";
                            } else if (item.equals("naprawa drobnego AGD")) {
                                subcategory = "naprawa_drobnego_AGD";
                            } else if (item.equals("naprawa AGD")) {
                                subcategory = "naprawa_AGD";
                            } else if (item.equals("naprawa RTV")) {
                                subcategory = "naprawa_RTV";
                            } else if (item.equals("naprawa komputerów/laptopów")) {
                                subcategory = "naprawa_komputerow_laptopow";
                            } else if (item.equals("malowanie")) {
                                subcategory = "malowanie";
                            } else if (item.equals("tapetowanie")) {
                                subcategory = "tapetowanie";
                            } else if (item.equals("kładzenie kafelek")) {
                                subcategory = "kladzenie_kafelek";
                            } else if (item.equals("kładzenie paneli podłogowych")) {
                                subcategory = "kladzenie_paneli_podlogowych";
                            }


                            if(dataSnapshot.child("categories").child(temp).child("state").getValue().toString()=="true"){
                                Log.v ("Bylem tu", " Ld");

                                String category = "";

                                if(temp.equals("sub1") || temp.equals("sub2")){
                                    category= "hydraulika";
                                }else if (temp.equals("sub3") || temp.equals("sub4")){
                                    category="elektryka";
                                }else if (temp.equals("sub5") || temp.equals("sub6") || temp.equals("sub7")){
                                    category="pomoc_domowa";
                                } else if(temp.equals("sub8")|| temp.equals("sub9")|| temp.equals("sub10")|| temp.equals("sub11")){
                                    category="opieka";
                                } else if (temp.equals("sub12")){
                                    category="korepetycje";
                                } else if (temp.equals("sub13")|| temp.equals("sub14")|| temp.equals("sub15")){
                                    category="ogrodnictwo";
                                } else if (temp.equals("sub16")|| temp.equals("sub17")|| temp.equals("sub18")|| temp.equals("sub19")){
                                    category="naprawa_urzadzen";
                                } else if (temp.equals("sub20")|| temp.equals("sub21")|| temp.equals("sub22")|| temp.equals("sub23")){
                                    category="remonty";
                                }

                                tempCategory = tempCategory + category + ", ";

                                DatabaseReference addUser = addUserToDatabaseReference.child(category).child(subcategory).child(uid);

                                addUser.child("name").setValue(name);
                                addUser.child("number").setValue(number);
                                addUser.child("desc").setValue(desc);
                                addUser.child("address").setValue(address);
                                addUser.child("email").setValue(email);
                                addUser.child("user_image").setValue(image);
                                addUser.child("uid").setValue(uid);
                                addUser.child("service_state").setValue(service_state);
                                addUser.child("rate").setValue("5.0f");
                                addUser.child("category").setValue(deleteLastChar(filterCategory));


                            }else if (dataSnapshot.child("categories").child(temp).child("state").getValue().toString()=="false"){
                                String category = "";

                                if(temp.equals("sub1") || temp.equals("sub2")){
                                    category= "hydraulika";
                                }else if (temp.equals("sub3") || temp.equals("sub4")){
                                    category="elektryka";
                                }else if (temp.equals("sub5") || temp.equals("sub6") || temp.equals("sub7")){
                                    category="pomoc_domowa";
                                } else if(temp.equals("sub8")|| temp.equals("sub9")|| temp.equals("sub10")|| temp.equals("sub11")){
                                    category="opieka";
                                } else if (temp.equals("sub12")){
                                    category="korepetycje";
                                } else if (temp.equals("sub13")|| temp.equals("sub14")|| temp.equals("sub15")){
                                    category="ogrodnictwo";
                                } else if (temp.equals("sub16")|| temp.equals("sub17")|| temp.equals("sub18")|| temp.equals("sub19")){
                                    category="naprawa_urzadzen";
                                } else if (temp.equals("sub20")|| temp.equals("sub21")|| temp.equals("sub22")|| temp.equals("sub23")){
                                    category="remonty";
                                }


                                DatabaseReference deleteUser = addUserToDatabaseReference.child(category).child(subcategory);
                                deleteUser.child(uid).removeValue();
                            }

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        userOldData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        userOldData();

        userSubCategoriesChoice();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        userSubCategoriesChoice();
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

        //addToDatabase
        addToDatabase = (Button)findViewById(R.id.addToDatabase);


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
                filterCategory = "";


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
