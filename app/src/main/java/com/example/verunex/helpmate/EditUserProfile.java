package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class EditUserProfile extends Activity {

    private TextView userChoice;

    private ImageButton user_image;
    private EditText user_name, user_number;
    private EditText user_address;
    private Button save, imageEdit;
    private ImageView addrImageView, exitImageView;

    private Uri mImageUri;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mCategoriesChoice;
    private StorageReference mStorageReference;

    private DatabaseReference mOldData;

    private static final int GALLERY_REQUEST = 1;

    private RequestQueue mRequestQueue;

    private LocationManager locationManager;

    private String TAG = "New ";

    String rText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_user_profile);

        /*ListView mListView= (ListView) findViewById(R.id.categoriesListView);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        mListView.setAdapter(listAdapter);
        */

       /* PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
*/
        mFirebaseAuth = FirebaseAuth.getInstance();

        String id = mFirebaseAuth.getCurrentUser().getUid();

        user_image = (ImageButton) findViewById(R.id.userImage);
        user_name = (EditText) findViewById(R.id.userNameEdit);
        user_number = (EditText) findViewById(R.id.userPhoneEdit);
        save = (Button) findViewById(R.id.save);
        imageEdit = (Button) findViewById(R.id.userImageEdit);

        //
        user_address = (EditText)findViewById(R.id.userAddressEdit);

        addrImageView = (ImageView) findViewById(R.id.addrImageView);

        mRequestQueue = Volley.newRequestQueue(this);

        // MAPA

        addrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);*/

                // Get LocationManager object
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Get Current Location
                if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location myLocation = locationManager.getLastKnownLocation(provider);

                if(myLocation == null){
                    user_address.setText("null");
                }else {
                    //latitude of location
                    double myLatitude = myLocation.getLatitude();

                    //longitude og location
                    double myLongitude = myLocation.getLongitude();

                    String Latitude = String.valueOf(myLatitude);
                    String Longtitude = String.valueOf(myLongitude);

                    Log.v ("Cordi", Latitude );

                    Log.v ("Cordi2", Longtitude );

                    JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + Latitude + "," + Longtitude + "&key=AIzaSyBWbcjYmZ3OVyklVuQFZOzUDzQMitkaKwc", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                user_address.setText(address);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    mRequestQueue.add(request);
                }

            }
        });


        mOldData = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(id);

        mOldData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                user_name.setText(name);

                String number = dataSnapshot.child("number").getValue(String.class);
                user_number.setText(number);

                String addres = dataSnapshot.child("address").getValue(String.class);
                user_address.setText(addres);

                //String address = dataSnapshot.child("")


                String user_image_uri = dataSnapshot.child("user_image").getValue(String.class);
                if(user_image_uri.isEmpty()){

                }else{
                    Picasso.with(getBaseContext()).load(user_image_uri).transform(new Circle()).into(user_image);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        mStorageReference = FirebaseStorage.getInstance().getReference().child("users_image");

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery_intent = new Intent();
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent, GALLERY_REQUEST);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateProfile();
            }
        });
    }

    private void startCreateProfile() {

        final String name = user_name.getText().toString().trim();
        final String number = user_number.getText().toString().trim();
        final String address = user_address.getText().toString().trim();


        final String user_id = mFirebaseAuth.getCurrentUser().getUid();

        if (mImageUri == null) {
            user_image.setImageResource(R.drawable.person);
            mDatabaseReference.child(user_id).child("name").setValue(name);
            mDatabaseReference.child(user_id).child("number").setValue(number);
            mDatabaseReference.child(user_id).child("address").setValue(address);
        } else {
            StorageReference filepath = mStorageReference.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseReference.child(user_id).child("name").setValue(name);
                    mDatabaseReference.child(user_id).child("number").setValue(number);
                    mDatabaseReference.child(user_id).child("address").setValue(address);
                    mDatabaseReference.child(user_id).child("user_image").setValue(downloadUri);
                }
            });
        }
        // testowo
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri = data.getData();

        //String allChoice = "";

        /*if (requestCode == 3) {
            if (resultCode == 2) {
                String dataCheckboxTexts[] = data.getStringArrayExtra("dataCheckBoxTexts");
                for(int i = 0; i<6; i++){
                        if(!Objects.equals(dataCheckboxTexts[i], "null")){
                            String temp = "- " + dataCheckboxTexts[i] +"\n";
                            allChoice = allChoice + temp;
                        }

                }

                userChoice.setText(allChoice);

            }
        }*/
            if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri= result.getUri();
                user_image.setImageURI(mImageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, BackPressPop.class);
        i.putExtra("temp", 3);
        startActivity(i);
    }
}
