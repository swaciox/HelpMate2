package com.example.verunex.helpmate;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubCategoryPop extends Activity {

    private ListView mListView;
    private String[] categories;
    private TextView mTextView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_subcategory);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;

        final String selected = getIntent().getStringExtra("Selected");
        Log.v ("cos ", selected);

        Resources res = getResources();

        int getRes = getResources().getIdentifier(selected, "array", getPackageName());

        categories = res.getStringArray(getRes);

        String length = "0."+categories.length+"2";
        float liczba = Float.parseFloat(length);
        //Log.v("Liczba ", String.valueOf(liczba));

        getWindow().setLayout((int) (width * .8), (int) (height * liczba));


        String temp = "";

        if (selected.equals("Naprawa_urzadzen")){
            temp = "Naprawa urządzeń";
        } else if (selected.equals("Pomoc_domowa")) {
            temp = "Pomoc domowa";
        } else if (selected.equals("Pomoc_naukowa")){
            temp = "Pomoc naukowa";
        } else {
            temp = selected;
        }

        mListView = (ListView) findViewById(R.id.listOfSubcategory);
        mTextView = (TextView)findViewById(R.id.cname);

        mTextView.setText(temp);

        mListView.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                categories));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos,   long id) {

                String item = (String) parent.getItemAtPosition(pos);

                Log.v ("Item" , item);
                    Intent i = new Intent(getApplicationContext(), CategoryList.class);
                    i.putExtra("Category", selected);
                    i.putExtra("Subcategory", item);
                    startActivity(i);
            }
        });
    }
}

