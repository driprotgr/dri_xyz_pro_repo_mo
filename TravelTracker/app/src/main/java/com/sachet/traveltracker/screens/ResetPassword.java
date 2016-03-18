package com.sachet.traveltracker.screens;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sachet.traveltracker.R;
import com.sachet.traveltracker.beans.AccountType;
import com.sachet.traveltracker.utils.Constants;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.Validation;
import com.sachet.traveltracker.utils.VolleyErrorHelper;
import com.sachet.traveltracker.utils.VolleySigleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    private String TAG = "ResetPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setActionBar();
        setViewObjects();
    }

    private void setActionBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViewObjects(){

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);

        ((TextInputLayout)findViewById(R.id.passwordWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.verificationTextWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.confirmPasswordWrapper)).setTypeface(roboto);



        final EditText password = (EditText)findViewById(R.id.password);

        final EditText verificationText = (EditText)findViewById(R.id.verificationText);

        final EditText confirmPassword = (EditText)findViewById(R.id.confirmPassword);

        Button resetPassword = ((Button)findViewById(R.id.resetPassword));
        resetPassword.setTypeface(roboto);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.hasText(verificationText) && Validation.hasText(password)  && Validation.hasText(confirmPassword)
                        && Validation.matchPassword(password,confirmPassword)) {
                    resetPasswordButtonClicked(password.getText().toString(),verificationText.getText().toString());
                }
            }
        });
    }

    private void resetPasswordButtonClicked(String password,String verificationText){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("password", password);
            jsonObject.put("otp", verificationText);
            jsonObject.put("accountType", AccountType.MOBILE.getValue());
            jsonObject.put("Mobile_No", getIntent().getExtras().getString("mobile_number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, Constants.VERIFY_MOBILE_REQUEST,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorDialogue.getDialogue(ResetPassword.this, "Error", VolleyErrorHelper.getMessage(error, getBaseContext()));
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            };
        };
        jsObjRequest.setTag(TAG);
        VolleySigleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
    }
}
