package com.example.verunex.helpmate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class FragmentSubPage1 extends Fragment implements OnMapReadyCallback{

    private TextView desc, number, numberSms, email, userCategories;
    private GoogleMap mMap;
    String address;

    private ImageButton phone, message, emailButton;
    //private SupportMapFragment map;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sub_page1, container, false);



       /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/

        desc = (TextView) view.findViewById(R.id.userDesc);
        number = (TextView) view.findViewById(R.id.number);
        numberSms = (TextView) view.findViewById(R.id.numberSms);
        email = (TextView) view.findViewById(R.id.userEmail);
        userCategories = (TextView) view.findViewById(R.id.userCategories);

        //
        phone = (ImageButton)view.findViewById(R.id.phone_number);
        message = (ImageButton)view.findViewById(R.id.message);
        emailButton = (ImageButton)view.findViewById(R.id.emailButton);

        // Bundle bundle = this.getArguments();

        if (this.getArguments() != null) {
            final String numberString = this.getArguments().getString("number");
            final String emailString = this.getArguments().getString("email");
            String descString = this.getArguments().getString("desc");
            String subcategory = this.getArguments().getString("subcategory");
            address = this.getArguments().getString("address");

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberString));
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", numberString);
                    smsIntent.putExtra("sms_body","Witam pisze z serwisu HelpMate!\n");
                    startActivity(smsIntent);
                }
            });

            emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailString });
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Zapytanie HelpMate");
                    intent.putExtra(Intent.EXTRA_TEXT, "Witam pisze z portalu HelpMate");
                    startActivity(Intent.createChooser(intent, ""));
                }
            });


            if(desc.equals("")){
                LinearLayout layout = (LinearLayout) getView().findViewById(R.id.description);
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                params.height = 0;
                layout.setLayoutParams(params);
            }else{
                desc.setText(descString);
            }

            number.setText(numberString);
            numberSms.setText(numberString);
            email.setText(emailString);
            userCategories.setText(subcategory);


        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.app.FragmentManager fragment = getActivity().getFragmentManager();
        final MapFragment mf = (MapFragment) fragment.findFragmentById(R.id.map);
        mf.getMapAsync(this);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;
        LatLng poznan = new LatLng(52.414688, 16.930498);
        // LatLng latLng = new LatLng(Double.parseDouble(getLatitude()), Double.parseDouble(getLongitude()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(poznan));
        googleMap.setMinZoomPreference(10);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        googleMap.setMyLocationEnabled(false);

        String location = address;

        List<Address> addressList = null;

        if (location != null || location != "") {

            Geocoder geocoder = new Geocoder(getContext());
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(address.equals("")){
                LinearLayout layout = (LinearLayout) getView().findViewById(R.id.locationUser);
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                    params.height = 0;
                    layout.setLayoutParams(params);

            }else{
                android.location.Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.setMinZoomPreference(15);
            }


        }

    }


   /* @Override
    public void onMapReady(GoogleMap googleMap) {


    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }*/
}
