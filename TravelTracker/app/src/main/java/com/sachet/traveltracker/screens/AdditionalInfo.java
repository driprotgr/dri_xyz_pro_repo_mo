package com.sachet.traveltracker.screens;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.sachet.traveltracker.R;
import com.sachet.traveltracker.beans.User;
import com.sachet.traveltracker.utils.Constants;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.ResponseCode;
import com.sachet.traveltracker.utils.Validation;
import com.sachet.traveltracker.utils.VolleyErrorHelper;
import com.sachet.traveltracker.utils.VolleySigleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AdditionalInfo extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;
    private String TAG = "AdditionalInfo";
    private RelativeLayout layout;
    private RelativeLayout inner;
    private EditText name;
    private EditText email;
    private EditText mobile;
    private TextView address;
    private Spinner genderList;
    private User user;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);
        layout = (RelativeLayout)findViewById(R.id.editTextLayout);
        inner = (RelativeLayout)findViewById(R.id.innerLayout);
        setActionBar();
        setViewObjects();
        autoFillValues();
        updateView(false);
    }

    private void autoFillValues(){

        Intent intent = getIntent();
        if(intent != null && intent.getExtras() == null){
            return;
        }

        user = User.fromJson(intent.getExtras().getString("user"));
        name.setText(user.getName());
        mobile.setText(user.getMobile());
        email.setText(user.getEmail());
    }

    private void checkBox(Typeface roboto){
        checkBox = (CheckBox)findViewById(R.id.driverCheckBox);
        checkBox.setTypeface(roboto);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckChange();
            }
        });
    }

    private void onCheckChange(){
        boolean checked = checkBox.isChecked();
        int visibility = View.INVISIBLE;
        if(checked){
            visibility = View.VISIBLE;
        }else {
            visibility = View.INVISIBLE;
        }
        updateView(checked);
//        drivingLicense.setVisibility(visibility);
    }

    private void updateView(boolean checked){

        if(checked){
            layout.addView(inner);
        }else {
            layout.removeView(inner);
        }
    }
/*
    private void drivingLicenseField(boolean checked){
        if(drivingLicense == null){
            drivingLicense = getEditText();
            drivingLicense.setHint(getResources().getString(R.string.licence_number));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.driverCheckBox);
            drivingLicense.setLayoutParams(params);
        }

        if(checked){
            layout.addView(drivingLicense);
        }else {
            layout.removeView(drivingLicense);
        }
    }
    private TextInputLayout getEditText(){
        TextInputLayout editTextLayout = new TextInputLayout(this);
        EditText editText = new EditText(new ContextThemeWrapper(this,R.style.editText));
        editTextLayout.addView(editText);
        return editTextLayout;
    }*/

    private void setActionBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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

    private void setGenderList(){

        genderList = (Spinner)findViewById(R.id.gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderList.setAdapter(adapter);
    }
    private void setViewObjects(){

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);
        ((TextInputLayout)findViewById(R.id.nameWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.emailWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.mobileNumberWrapper)).setTypeface(roboto);

        ((TextInputLayout)findViewById(R.id.drivingLicenceWrapper)).setTypeface(roboto);

        name = ((EditText)findViewById(R.id.name));
        email = ((EditText)findViewById(R.id.emailAddress));
        mobile = ((EditText)findViewById(R.id.mobileNumber));
        address = ((TextView)findViewById(R.id.address));
        address.setTypeface(roboto);
        checkBox(roboto);
        setGenderList();

        ((ImageButton)findViewById(R.id.pickAddress)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePickerRequest();
            }
        });

         Button getStartedButton = ((Button) findViewById(R.id.getStarted));
         getStartedButton.setTypeface(roboto);
         getStartedButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 updateUserInfo(user);
                 updateUserRequest();
             }
         });

    }

    private void updateUserRequest(){

        String _name = name.getText().toString();
        String _email = email.getText().toString();
        String _address = address.getText().toString();
        String _mobile = mobile.getText().toString();

        boolean required = checkBox.isChecked();
        if(Validation.hasText(name) && Validation.isEmailAddress(email,true)){

            if(Validation.isPhoneNumber(mobile,required)){

                if(required && (_address == null || _address.isEmpty())){
                    showErrorPopUp("Login Error","Please select your location");
                }else {
                    user.setName(_name);
                    user.setEmail(_email);
                    user.setMobile(_mobile);
//                    user.setL
                    updateUserInfo(user);
                }

            }

        }

//


    }
    private void updateUserInfo(final User user){

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.UPDATE_USER_REQUEST,user.toJson(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            processLoginResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showErrorPopUp("Error", VolleyErrorHelper.getMessage(error, getBaseContext()));
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                };
            };
            jsObjRequest.setTag(TAG);
            VolleySigleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsObjRequest);
        }

    private void processLoginResponse(JSONObject response){
        try{
            int responseCode = response.getInt("responseCode");
            switch (responseCode){

                case ResponseCode.SUCCESS:

                    User user = User.fromJson(response.getString("responseBody"));

                    break;

                default:
                    showErrorPopUp("Login Error",response.getString("responseMessage"));
                    break;
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }


    private void placePickerRequest(){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
            showErrorPopUp("Service Unavailable", "Google Play Services Repairable");
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            showErrorPopUp("Service Unavailable","Google Play Services NotAvailable");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String address = String.format("Place: %s", place.getName());
                address.setText(place.getName());

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this," Canceled by user", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this," onActivityResult "+resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showErrorPopUp(String title,String message){
        ErrorDialogue.getDialogue(AdditionalInfo.this,title,message);
    }
}
