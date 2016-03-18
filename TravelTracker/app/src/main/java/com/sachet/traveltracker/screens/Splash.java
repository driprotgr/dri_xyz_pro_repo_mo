package com.sachet.traveltracker.screens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.iid.InstanceID;
import com.sachet.traveltracker.R;
import com.sachet.traveltracker.fragments.ConfirmOneTimePasswordFragment;
import com.sachet.traveltracker.services.RegistrationIntentService;
import com.sachet.traveltracker.utils.Constants;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.InputDialogue;
import com.sachet.traveltracker.utils.VolleyErrorHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//public class Splash extends Activity {
public class Splash extends AppCompatActivity {
    private String TAG = "TAG";
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new SplashBackgroundAdapter(Splash.this);
        viewPager.setAdapter(adapter);
        setViewObjects();

        setDeviceId(Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
//        printKeyHash(this);
        startGCMService();
    }

    private void startGCMService(){
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    private void setDeviceId(String deviceId){
        Constants.DEVICE_ID = deviceId;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        debug("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        debug("onSaveInstanceState");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        debug("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        debug("onResume");
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        debug("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        debug("onPause");
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    void setViewObjects(){

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);

        Button searchDriver = (Button)findViewById(R.id.searchDriver);

        searchDriver.setTypeface(roboto);

        searchDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("search driver");
//                printKeyHash(Splash.this);
//                new ConfirmOneTimePasswordFragment().show(getSupportFragmentManager(), "confirm otp");
//                ErrorDialogue.getDialogue(Splash.this, "Error","Message is here");
            }
        });

        Button signUp = (Button)findViewById(R.id.signUp);

        signUp.setTypeface(roboto);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("sign up");
                startActivity(new Intent(getBaseContext(), SignUp.class));
            }
        });

        Button signIn = (Button)findViewById(R.id.signIn);

        signIn.setTypeface(roboto);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("log in");
//                AlertDialog dialogue = ErrorDialogue.getDialogue(Splash.this,"Title","This will be your error message");
                startActivity(new Intent(getBaseContext(),Login.class));
            }
        });
    }


    void debug(String message){
        System.out.println("Splash Screen >> "+message);
    }
}
