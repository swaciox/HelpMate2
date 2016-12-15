package com.example.verunex.helpmate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesChoice extends AppCompatActivity {

    private Button mButton;

    private CheckBox choice1, choice2, choice3, choice4, choice5, choice6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_choice);

        choice1 = (CheckBox)findViewById(R.id.choice1);
        choice2 = (CheckBox)findViewById(R.id.choice2);
        choice3 = (CheckBox)findViewById(R.id.choice3);
        choice4 = (CheckBox)findViewById(R.id.choice4);
        choice5 = (CheckBox)findViewById(R.id.choice5);
        choice6 = (CheckBox)findViewById(R.id.choice6);

        final CheckBox[] checkBoxes = {choice1,choice2,choice3,choice4,choice5,choice6};

        /*for (int i = 0; i < 6 ; i++) {
            String temp  = "choice" + i+1 ;
            int id = getResources().getIdentifier(temp, "id", getApplicationContext().getPackageName());
            Log.v("KOMUNIKAT", String.valueOf(id));
            CheckBox temp1 = (CheckBox)findViewById(id);
            checkBoxes[i] = temp1.getText().toString();
            //checkBoxes[i].setChecked(false);
        }*/

        mButton = (Button)findViewById(R.id.acceptCategories);

       mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String[] checkBoxTexts = new String[6];
               for (int i = 0; i < 6; i++) {

                   if (checkBoxes[i].isChecked()) {
                       String text = (String) checkBoxes[i].getText();
                       checkBoxTexts[i] = text;
                   }else{
                       checkBoxTexts[i] = "null";
                   }
               }
               Intent dataCheckbox = new Intent (getBaseContext(), EditUserProfile.class);
               dataCheckbox.putExtra("dataCheckBoxTexts",checkBoxTexts);
               setResult(2, dataCheckbox);
               //startActivity(dataCheckbox);
               finish();

           }
       });
    }
}
