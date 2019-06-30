package com.unibs.zanotti.inforinvestigador.services;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = String.valueOf(MyFirebaseMessagingService.class);
    public static final String MESSAGES_CHANNEL = "testChannel";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }
}
