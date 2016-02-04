package com.sachet.traveltracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Splash extends Activity {
    ViewPager viewPager;
    Typeface roboto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomAdapter(Splash.this);
        viewPager.setAdapter(adapter);
        setButtons();
    }

    void setButtons(){
//        roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Button searchDriver = (Button)findViewById(R.id.searchDriver);
//        searchDriver.setTypeface(roboto);

        searchDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("search driver");
            }
        });

        Button signUp = (Button)findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("sign up");
            }
        });

        Button signIn = (Button)findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug("log in");

                startActivity(new Intent(getBaseContext(),LoginActivity.class));
            }
        });
    }


    void debug(String message){
        System.out.println("Splash Screen >> "+message);
    }
}
