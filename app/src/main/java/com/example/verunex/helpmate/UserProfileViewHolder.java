package com.example.verunex.helpmate;

import android.content.Context;
import android.support.design.widget.NavigationView;
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

    public UserProfileViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

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

    public void setCategory(String category){
            TextView user_category = (TextView) mView.findViewById(R.id.category);
            user_category.setText(category);
    }

    public void setImage(Context ctx, String image){
        ImageView user_img = (ImageView) mView.findViewById(R.id.image);
        Picasso.with(ctx).load(image).into(user_img);
    }
   // public void setRate(String rate){
   //     RatingBar user_rate = (RatingBar) mView.findViewById(R.id.ratingBar);
    //    user_rate.setRating(Float.parseFloat(rate));
   // }
    //public String setDescription(String description){
    //   return description;
    //}
}