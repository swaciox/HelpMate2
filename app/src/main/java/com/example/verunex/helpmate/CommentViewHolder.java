package com.example.verunex.helpmate;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CommentViewHolder extends RecyclerView.ViewHolder{

    View mView;

    ImageButton mImageButton;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        mImageButton = (ImageButton) mView.findViewById(R.id.imageButtonExit);
    }

    public void setDate (String date){
        TextView comment_date = (TextView) mView.findViewById(R.id.commentDate);
        comment_date.setText(date);
    }

    public void setDesc (String desc){
        TextView comment_description = (TextView) mView.findViewById(R.id.commentDesc);
        comment_description.setText(desc);
    }


    public void setName (String name){
        TextView comment_name = (TextView) mView.findViewById(R.id.commentName);
        comment_name.setText(name);
    }

    // test

    public void setUser_id (String user_id){
        /*TextView comment_name = (TextView) mView.findViewById(R.id.commentName);
        comment_name.setText(user_id);*/
    }

    public void setUserImage(Context ctx, String user_image){
        ImageView user_img = (ImageView) mView.findViewById(R.id.commentImage);
        if(user_image.equals("")){
            Picasso.with(ctx).load("https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/users_image%2Fperson.png?alt=media&token=643855bc-4dd4-4a92-8830-880da677ce7f").transform(new Circle()).into(user_img);
        }else {
            Picasso.with(ctx).load(user_image).transform(new Circle()).into(user_img);

        }

    }

    public void setRate (String rate){
        RatingBar comment_rate = (RatingBar)mView.findViewById(R.id.commentRatingBar);
        comment_rate.setRating(Float.parseFloat(rate));
    }

}
