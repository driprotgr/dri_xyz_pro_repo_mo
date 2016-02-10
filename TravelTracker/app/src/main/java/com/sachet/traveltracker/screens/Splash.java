package com.sachet.traveltracker.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sachet.traveltracker.R;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.InputDialogue;

public class Splash extends Activity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomAdapter(Splash.this);
        viewPager.setAdapter(adapter);
        setButtons();
    }

    void setButtons(){

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);

        Button searchDriver = (Button)findViewById(R.id.searchDriver);

        searchDriver.setTypeface(roboto);

        searchDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("search driver");
                InputDialogue.createInputDialogue(Splash.this,"New User Input" ,new InputDialogue.UserActionListener() {
                    @Override
                    public void onPositiveAction(String text) {
                        debug("text input recieved " + text);
                    }
                });
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
                AlertDialog dialogue = ErrorDialogue.getDialogue(Splash.this,"Title","This will be your error message");
//                startActivity(new Intent(getBaseContext(),Login.class));
            }
        });
    }


    void debug(String message){
        System.out.println("Splash Screen >> "+message);
    }
}
