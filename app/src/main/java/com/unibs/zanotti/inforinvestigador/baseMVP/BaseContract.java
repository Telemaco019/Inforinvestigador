package com.unibs.zanotti.inforinvestigador.baseMVP;


import android.os.Bundle;
import androidx.lifecycle.Lifecycle;

/**
 * Created by Chatikyan on 20.05.2017.
 */

public interface BaseContract {

    interface View {

    }

    interface Presenter<V extends BaseContract.View> {

        Bundle getStateBundle();

        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        /**
         * Called when a new instance of the presenter is created
         */
        void onPresenterCreated();

        void onPresenterDestroy();

        /**
         * Called when the activity associated to the presenter is stopped
         */
        void onStop();

        /**
         * Supposed to be called when the activity associated to the presenter is started. Use this method for showing
         * in the view the data previously retrieved by the presenter
         */
        void onStart();
    }
}
