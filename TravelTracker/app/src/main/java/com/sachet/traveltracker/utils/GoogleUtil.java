package com.sachet.traveltracker.utils;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by lenovo on 11-02-2016.
 */
public class GoogleUtil {

    public static final int RC_SIGN_IN = 9001;
    public static GoogleApiClient init(Context context){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
//                .enableAutoManage(context /* FragmentActivity */, context /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        return mGoogleApiClient;
    }
}
