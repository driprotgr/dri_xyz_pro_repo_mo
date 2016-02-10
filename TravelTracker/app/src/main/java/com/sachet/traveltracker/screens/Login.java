package com.sachet.traveltracker.screens;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sachet.traveltracker.R;
//import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.Validation;

public class Login extends Activity {
    ImageButton eyeButton;
    EditText password;
    EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setButtons();
    }
    void setButtons(){

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
                debug("back button");
            }
        });

        TextView forgotPassword = (TextView)findViewById(R.id.forgotPassword);

        forgotPassword.setTypeface(roboto);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("forgot passwords button");
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

        Button signInButton = (Button)findViewById(R.id.signIn);

        signInButton.setTypeface(roboto);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("sign in button");
//                AlertDialog dialog = ErrorDialogue.getDialogue(Login.this, "Error Title", "This is a error dialogue message");

                if (Validation.isPhoneNumber(mobileNumber, true) && Validation.hasText(password)) {
                    debug("validated");
                }
            }
        });

        TextView signUpButton = (TextView)findViewById(R.id.signUp);

        signUpButton.setTypeface(roboto);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("sign up button");
                startActivity(new Intent(getBaseContext(), SignUp.class));
            }
        });

        eyeButton = (ImageButton)findViewById(R.id.eyeButton);

        eyeButton.setOnClickListener(new View.OnClickListener() {
            boolean isOpen = false;

            @Override
            public void onClick(View v) {

                debug("eye button");

                isOpen = !isOpen;
                eyeClicked(isOpen);

            }
        });

        ((TextInputLayout)findViewById(R.id.passwordWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.mobileNumberWrapper)).setTypeface(roboto);

        password = (EditText)findViewById(R.id.password);

        mobileNumber = (EditText)findViewById(R.id.mobileNumber);

        ((TextView)findViewById(R.id.welcomeText)).setTypeface(roboto);

        ((TextView)findViewById(R.id.destinationText)).setTypeface(roboto);

        ((TextView)findViewById(R.id.newUser)).setTypeface(roboto);

        ((TextView)findViewById(R.id.dividerText)).setTypeface(roboto);
    }

    void eyeClicked(boolean isOpen){
        if(isOpen){
            eyeButton.setBackgroundResource(R.drawable.eye_close);
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            eyeButton.setBackgroundResource(R.drawable.eye_open);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        password.setSelection(password.getText().length());
    }
    void backButton(){
        this.finish();
    }
    void debug(String message){
        System.out.println("Login Screen >> " + message);
    }
}
