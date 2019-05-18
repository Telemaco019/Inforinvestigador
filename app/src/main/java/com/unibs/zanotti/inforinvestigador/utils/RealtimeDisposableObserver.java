package com.unibs.zanotti.inforinvestigador.utils;

import io.reactivex.observers.DisposableObserver;

public abstract class RealtimeDisposableObserver extends DisposableObserver {
    /**
     * Called when all the data present in the database has been loaded and the observer, from now on, will thus receive
     * only real-time updated (it is assumed that on each update {@link DisposableObserver#onNext(Object)} will be called
     */
    abstract void onDataLoaded();
}
