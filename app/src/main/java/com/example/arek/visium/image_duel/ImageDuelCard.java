package com.example.arek.visium.image_duel;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.arek.visium.R;
import com.example.arek.visium.model.Profile;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.squareup.picasso.Picasso;

/**
 * Created by arek on 7/27/2017.
 */

@Layout(R.layout.duel_card_view)
public class ImageDuelCard {

    @View(R.id.duel_image_view)
    private ImageView duelImageView;
    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public ImageDuelCard(Profile mProfile, Context mContext, SwipePlaceHolderView mSwipeView) {
        this.mProfile = mProfile;
        this.mContext = mContext;
        this.mSwipeView = mSwipeView;
    }

    @Resolve
    private void onResolved(){

        Picasso.with(mContext).load(mProfile.getImageUrl()).into(duelImageView);

    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

}
