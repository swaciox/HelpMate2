package com.example.verunex.helpmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Verunex on 2017-01-05.
 */

public class User2ProfileViewHolder extends RecyclerView.ViewHolder {

    View mView;
    CheckBox favouriteBox;
    ImageButton btn;
    ImageButton btn2;
    ImageButton btn3;


    public User2ProfileViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        btn3 = (ImageButton)mView.findViewById(R.id.email);
        btn2 = (ImageButton)mView.findViewById(R.id.message);
        btn = (ImageButton)mView.findViewById(R.id.call);
        favouriteBox = (CheckBox)mView.findViewById(R.id.checkBox);

    }

    public String setSubcategory(String subcategory){
        return subcategory;
    }

    public String setDesc (String desc){
        return desc;
    }
    public String setAddress (String address){
        return address;
    }
    public String setUid(String uid){
        return uid;
    }
    public String setServiceState (String service_state) {
        return service_state;
    }
    public void setName(String name){
        TextView user_name = (TextView) mView.findViewById(R.id.name);
        user_name.setText(name);
    }
    public String setNumber (String number){
        return number;
    }

    public String setEmail (String email) {return email;}

    public void setCategory(String category){
        TextView user_category = (TextView) mView.findViewById(R.id.category);
        user_category.setText(category);
    }

    public void setImage(Context ctx, String image){
        ImageView user_img = (ImageView) mView.findViewById(R.id.image);

        if(image.equals("")){
            Picasso.with(ctx).load("https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/users_image%2Fperson.png?alt=media&token=643855bc-4dd4-4a92-8830-880da677ce7f").transform(new Circle()).into(user_img);
        }else {
            Picasso.with(ctx).load(image).transform(new Circle()).into(user_img);

        }


    }

    public void setRate(String rate){
        RatingBar user_rate = (RatingBar) mView.findViewById(R.id.commentRatingBar);
        user_rate.setRating(Float.parseFloat(rate));
    }
}
