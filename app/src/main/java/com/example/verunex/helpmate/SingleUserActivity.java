package com.example.verunex.helpmate;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleUserActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private TextView mUserName, mUserCategory, mUserNumber, mDescription;
    private ImageView mImageView;

    String id_position;
    String user_id;
    String number, email, desc, subcategory, address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

       // String Id_key = getIntent().getExtras().getString("Id_key");

        String user_name = getIntent().getExtras().getString("user_name");
        String user_category =getIntent().getExtras().getString("user_category");
        String user_number = getIntent().getExtras().getString("user_number");
        String image = getIntent().getExtras().getString("user_image");
        String rate = getIntent().getExtras().getString("user_rate");
        number = getIntent().getExtras().getString("number");
        email = getIntent().getExtras().getString("email");
        desc = getIntent().getExtras().getString("desc");
        subcategory = getIntent().getExtras().getString("subcategory");
        address = getIntent().getExtras().getString("address");

        setTitle(user_name);

        user_id = getIntent().getExtras().getString("user_id");
        id_position = getIntent().getExtras().getString("id_position");
        //String user_description = getIntent().getExtras().getString("user_description");



        RatingBar user_rate = (RatingBar) findViewById(R.id.commentRatingBar);
        user_rate.setRating(Float.parseFloat(rate));

        mUserName = (TextView)findViewById(R.id.user_name);

        mUserCategory = (TextView)findViewById(R.id.user_category);
        mImageView = (ImageView)findViewById(R.id.user_image);
        mDescription = (TextView)findViewById(R.id.description);

        Picasso.with(getBaseContext()).load(image).transform(new Circle()).into(mImageView);
        mUserName.setText(user_name);
        mUserCategory.setText(user_category);
        //mDescription.setText(user_description);
        //mUserNumber.setText(user_number);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_user_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    FragmentSubPage1 fragmentSubPage1 = new FragmentSubPage1();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("number", number);
                    bundle2.putString("desc", desc );
                    bundle2.putString("email", email);
                    bundle2.putString("subcategory", subcategory);
                    bundle2.putString("address", address);
                    fragmentSubPage1.setArguments(bundle2);
                    return fragmentSubPage1;
                case 1:
                    FragmentSubPage2 fragmentSubPage2 = new FragmentSubPage2();
                    //send data
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", user_id);
                    bundle.putString("id_position", id_position);
                    fragmentSubPage2.setArguments(bundle);
                    return fragmentSubPage2;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "O MNIE";
                case 1:
                    return "OPINIE";
            }
            return null;
        }
    }
}
