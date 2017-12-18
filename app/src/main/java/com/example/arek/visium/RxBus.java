package com.example.arek.visium;

import com.example.arek.visium.screens.image_duel.SwipedItemParams;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by arek on 10/5/2017.
 */

public final class RxBus {

    private RxBus() {
    }

    private static PublishSubject<SwipedItemParams> bus = PublishSubject.create();

    public static void publishSwipedItemParams(SwipedItemParams swipedItemParams){
        bus.onNext(swipedItemParams);
    }

    public static Observable<SwipedItemParams> swipeActionObservable(){
        return bus;
    }

}
