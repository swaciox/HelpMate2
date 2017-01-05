package com.example.verunex.helpmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class UserProfileViewHolder extends RecyclerView.ViewHolder {

    View mView;
    CheckBox favouriteBox;
    ImageButton btn;
    ImageButton btn2;
    ImageButton btn3;

    public UserProfileViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        btn3 = (ImageButton)mView.findViewById(R.id.email);
        btn2 = (ImageButton)mView.findViewById(R.id.message);
        btn = (ImageButton)mView.findViewById(R.id.call);
        favouriteBox = (CheckBox)mView.findViewById(R.id.checkBox);

    }


    public String setUser_id (String user_id){
        return user_id;
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
        Picasso.with(ctx).load(image).transform(new Circle()).into(user_img);
    }
    public void setRate(String rate){
        RatingBar user_rate = (RatingBar) mView.findViewById(R.id.commentRatingBar);
        user_rate.setRating(Float.parseFloat(rate));
    }
    //public String setDescription(String description){
    //   return description;
    //}
}