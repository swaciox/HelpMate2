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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentSubPage1 extends Fragment{

    private ImageButton mImageButton;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private CheckBox checkBox;
    String cureent_user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        mFirebaseAuth = FirebaseAuth.getInstance();
        cureent_user_id = mFirebaseAuth.getCurrentUser().getUid();

        View rootView = inflater.inflate(R.layout.fragment_sub_page1, container, false);

        checkBox = (CheckBox)rootView.findViewById(R.id.checkBox2);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_from_list_id = "U4FXaHpWiIVJxfPgiAhwR2YxDnw1";

                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(cureent_user_id);

                final DatabaseReference favorite = mDatabaseReference.child("favorite");

                if(checkBox.isChecked()==true) {
                    favorite.child(user_from_list_id).setValue(user_from_list_id);
                    Toast.makeText(getContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                }else{
                    favorite.child(user_from_list_id).removeValue();
                    Toast.makeText(getContext(), "Usunięto z ulubionych!", Toast.LENGTH_SHORT).show();
                }
            }
        });

       // final String user_number = getArguments().getString("user_number");

        /*mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }

        });*/

        return rootView;
    }

}
