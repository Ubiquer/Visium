package com.example.arek.visium.screens.image_duel;

/**
 * Created by arek on 10/22/2017.
 */

public class SwipedItemParams {

    private int viewholderAdapterPosition;
    private int direction;

    public SwipedItemParams(int viewholderAdapterPosition, int direction) {
        this.viewholderAdapterPosition = viewholderAdapterPosition;
        this.direction = direction;
    }

    public int getViewholderAdapterPosition() {
        return viewholderAdapterPosition;
    }

    public int getDirection() {
        return direction;
    }
}
