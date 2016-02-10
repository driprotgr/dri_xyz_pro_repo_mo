package com.sachet.traveltracker.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sachet.traveltracker.R;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.Validation;

public class SignUp extends Activity {

    EditText password;
    EditText mobileNumber;
    EditText confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setButtons();
    }

    void setButtons() {

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);
        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
                debug("back button");
            }
        });

        ImageButton googleButton = (ImageButton)findViewById(R.id.googleButton);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("google button");
            }
        });

        ImageButton facebookButton = (ImageButton)findViewById(R.id.facebookButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("facebook button");
            }
        });

        Button signUpButton = (Button)findViewById(R.id.signUp);

        signUpButton.setTypeface(roboto);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("sign up button");
            }
        });

        TextView signInButton = (TextView)findViewById(R.id.signIn);

        signInButton.setTypeface(roboto);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("sign in button");
                startActivity(new Intent(getBaseContext(), Login.class));
            }
        });

        ((TextInputLayout)findViewById(R.id.passwordWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.mobileNumberWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.confirmPasswordWrapper)).setTypeface(roboto);



        password = (EditText)findViewById(R.id.password);

        mobileNumber = (EditText)findViewById(R.id.mobileNumber);

        confirmPassword = (EditText)findViewById(R.id.confirmPassword);

        ((TextView)findViewById(R.id.fillUpDetail)).setTypeface(roboto);

        ((TextView)findViewById(R.id.connectWithUs)).setTypeface(roboto);

        ((TextView)findViewById(R.id.alreadyRegistered)).setTypeface(roboto);

        ((TextView)findViewById(R.id.dividerText)).setTypeface(roboto);
    }

    void backButton(){
        this.finish();
    }
    void debug(String message){
        System.out.println("Sign Up Screen >> " + message);
    }
}
