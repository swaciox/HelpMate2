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

        if (categories.length <=4 && categories.length > 3)
        {
            String length = "0.40";
            float liczba = Float.parseFloat(length);
            getWindow().setLayout((int) (width * .8), (int) (height * liczba));

        } else if (categories.length <= 3 && categories.length > 2)
        {
            String length = "0.32";
            float liczba = Float.parseFloat(length);
            getWindow().setLayout((int) (width * .8), (int) (height * liczba));

        } else if (categories.length <= 2 && categories.length > 1)
        {
            String length = "0.25";
            float liczba = Float.parseFloat(length);
            getWindow().setLayout((int) (width * .8), (int) (height * liczba));

        }
        else if (categories.length <=1)
        {
            String length = "0.18";
            float liczba = Float.parseFloat(length);
            getWindow().setLayout((int) (width * .8), (int) (height * liczba));

        }

        String temp = "";

        if (selected.equals("naprawa_urzadzen")){
            temp = "Naprawa urządzeń";
        } else if (selected.equals("pomoc_domowa")) {
            temp = "Pomoc domowa";
        } else if (selected.equals("pomoc_naukowa")){
            temp = "Pomoc naukowa";
        } else if (selected.equals("elektryka")){
            temp = "Elektryka";
        } else if (selected.equals("hydraulika")){
            temp = "Hydraulika";
        } else if (selected.equals("opieka")){
            temp = "Opieka";
        } else if (selected.equals("ogrodnictwo")){
            temp = "Ogrodnictwo";
        } else if (selected.equals("remonty")){
            temp = "Remonty";
        }

        mListView = (ListView) findViewById(R.id.listOfSubcategory);
        mTextView = (TextView)findViewById(R.id.cname);

        mTextView.setText(temp);

        mListView.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.single_listview,
                categories));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos,   long id) {

                String item = (String) parent.getItemAtPosition(pos);
                String temp = "";

                if (item.equals("Naprawa wycieków")){
                    temp = "naprawa_wyciekow";
                } else if (item.equals("Wymiana armatury")) {
                    temp = "wymiana_armatury";
                } else if (item.equals("Instalacje elektryczne")){
                    temp = "instalacje_elektryczne";
                } else if (item.equals("Naprawa awaryjna")){
                    temp = "naprawa_awaryjna";
                } else if (item.equals("Sprzątanie")){
                    temp = "sprzatanie";
                } else if (item.equals("Prasowanie")){
                    temp = "prasowanie";
                } else if (item.equals("Mycie okien")){
                    temp = "mycie_okien";
                } else if (item.equals("Opieka do dzieci")) {
                    temp = "opieka_do_dzieci";
                } else if (item.equals("Opieka do osób starszych")) {
                    temp = "opieka_do_osob_starszych";
                } else if (item.equals("Opieka dzieci i osób niepełnosprawnych")) {
                    temp = "opieka_dzieci_i_osob_niepelnosprawnych";
                } else if (item.equals("Wyprowadzanie zwierząt")) {
                    temp = "wyprowadzanie_zwierzat";
                } else if (item.equals("Korepetycje")) {
                    temp = "korepetycje";
                } else if (item.equals("Koszenie trawy")) {
                    temp = "koszenie_trawy";
                } else if (item.equals("Prace porządkowe")) {
                    temp = "prace_porzadkowe";
                } else if (item.equals("Pielęgnacja ogrodu")) {
                    temp = "pielegnacja_ogrodu";
                } else if (item.equals("Naprawa drobnego AGD")) {
                    temp = "naprawa_drobnego_AGD";
                } else if (item.equals("Naprawa AGD")) {
                    temp = "naprawa_AGD";
                } else if (item.equals("Naprawa RTV")) {
                    temp = "naprawa_RTV";
                } else if (item.equals("Naprawa komputerów/laptopów")) {
                    temp = "naprawa_komputerow_laptopow";
                } else if (item.equals("Malowanie")) {
                    temp = "malowanie";
                } else if (item.equals("Tapetowanie")) {
                    temp = "tapetowanie";
                } else if (item.equals("Kładzenie kafelek")) {
                    temp = "kladzenie_kafelek";
                } else if (item.equals("Kładzenie paneli podłogowych")) {
                    temp = "kladzenie_paneli_podlogowych";
                }

                Log.v ("Item" , temp);
                    Intent i = new Intent(getApplicationContext(), CategoryList.class);
                    i.putExtra("Category", selected);
                    i.putExtra("Subcategory", temp);
                    startActivity(i);
                    finish();
            }
        });
    }
}

