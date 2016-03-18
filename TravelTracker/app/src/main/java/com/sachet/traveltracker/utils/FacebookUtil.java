package com.sachet.traveltracker.utils;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by lenovo on 13-02-2016.
 */
public class FacebookUtil {

    public static CallbackManager getCallBackManager(Activity activity,final FacebookUtilCallback callback){
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        callback.onSuccess(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        callback.onCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        callback.onError(exception);
                    }
                });
        return callbackManager;
    }

    public static void loginWithReadPermissions(Activity activity){
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));//"public_profile", "user_friends", "email"
    }

    public interface FacebookUtilCallback{
        void onSuccess(LoginResult loginResult);
        void onCancel();
        void onError(FacebookException exception);
    }
}
