package com.sachet.traveltracker.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;

import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sachet.traveltracker.R;
import com.sachet.traveltracker.beans.AccountType;
import com.sachet.traveltracker.beans.User;
import com.sachet.traveltracker.fragments.ConfirmOneTimePasswordFragment;
import com.sachet.traveltracker.utils.Constants;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FacebookUtil;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.GoogleUtil;
import com.sachet.traveltracker.utils.InputDialogue;
import com.sachet.traveltracker.utils.ResponseCode;
import com.sachet.traveltracker.utils.Validation;
import com.sachet.traveltracker.utils.VolleyErrorHelper;
import com.sachet.traveltracker.utils.VolleySigleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,ConfirmOneTimePasswordFragment.OnClickListener{

    private User user;
    CallbackManager callbackManager;
    EditText password;
    EditText mobileNumber;
    EditText confirmPassword;
    GoogleApiClient mGoogleApiClient;
    String TAG = "Sign Up Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setActionBar();
        setViewObjects();

        mGoogleApiClient = GoogleUtil.init(this);
        setCallbackManager();
    }

    private void setActionBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void jsonRequest(JSONObject json,final String requestMethod){

//        final TextView mTextView = (TextView)findViewById(R.id.fillUpDetail);

//        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, requestMethod,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        mTextView.setText(mTextView.getText().toString() + response.toString());
                        processLoginResponse(response,requestMethod);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mTextView.setText(error.toString());
//                        System.out.println("Volley Error " + error.getMessage());
//                        Log.e(TAG,error.getMessage());
                        ErrorDialogue.getDialogue(SignUp.this, "Error", VolleyErrorHelper.getMessage(error, getBaseContext()));
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            };
        };
//        queue.add(jsObjRequest);
        jsObjRequest.setTag(TAG);
        VolleySigleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        VolleySigleton.getInstance(getApplicationContext()).getRequestQueue().cancelAll(TAG);
    }

    private void requestMobileVerification(User user,String otp){
//        JSONObject jsonObject = new JSONObject();
//        try{
//            jsonObject.put("accountId", user.getAccountId());
//            jsonObject.put("otp", user.getOtp());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        user.setOtp(otp);
        jsonRequest(user.toJson(), Constants.CONFIRM_OTP_REQUEST);
    }


    private void mobileVerificationPopUp(User user){
        new ConfirmOneTimePasswordFragment().show(getSupportFragmentManager(), "confirm otp");
        this.user = user;
    }


    private void processLoginResponse(JSONObject response,String requestMethod){
        try{
            int responseCode = response.getInt("responseCode");
            switch (responseCode){

                case ResponseCode.SUCCESS:

                    User user = User.fromJson(response.getString("responseBody"));

                    if(requestMethod.equals(Constants.SIGNUP_REQUEST)){

                        mobileVerificationPopUp(user);
                        return;
                    }

                    if(response.getString("responseMessage").equals("NEW_USER") || requestMethod.equals(Constants.CONFIRM_OTP_REQUEST)){

                        launchAdditionalInfoActivity(user);
                        return;
                    }
                    launchDashboard(user);
                    break;

                default:
                    ErrorDialogue.getDialogue(SignUp.this, "Login Error", response.getString("responseMessage"));
                    break;
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void launchDashboard(User user){
        Intent intent = new Intent(getBaseContext(),Dashboard.class);

        if(user != null){
            intent.putExtra("user",user.toJson().toString());
        }

        startActivity(intent);
    }
    private void launchAdditionalInfoActivity(User user){

        Intent intent = new Intent(getBaseContext(),AdditionalInfo.class);

        if(user != null){
            intent.putExtra("user",user.toJson().toString());
        }

        startActivity(intent);
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
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void setCallbackManager(){
        callbackManager = FacebookUtil.getCallBackManager(this, new FacebookUtil.FacebookUtilCallback() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                User user = new User();
                user.setAccountType(AccountType.FACEBOOK);
                user.setAccountId(profile.getId());
                user.setName(profile.getName());
                jsonRequest(user.toJson(),Constants.LOGIN_REQUEST);
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"On onCancel");
                showToast(getString(R.string.login_cancelled));
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG,"On onError");
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GoogleUtil.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleUtil.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount googleUser = result.getSignInAccount();
            User user = new User();

            user.setName(googleUser.getDisplayName());
            user.setEmail(googleUser.getEmail());
            user.setAccountId(googleUser.getId());
            user.setAccountType(AccountType.GOOGLE);
            jsonRequest(user.toJson(),Constants.LOGIN_REQUEST);
//            showToast(acct.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
            showToast(getString(R.string.authentication_failed));
        }
    }

    void setViewObjects() {

        Typeface roboto = Fonts.getFont(getBaseContext(), FontType.REGULAR);
//        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
//
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                backButton();
//                debug("back button");
//            }
//        });

        ImageButton googleButton = (ImageButton)findViewById(R.id.googleButton);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("google button");
                signIn();
            }
        });

        ImageButton facebookButton = (ImageButton)findViewById(R.id.facebookButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("facebook button");

                FacebookUtil.loginWithReadPermissions(SignUp.this);
            }
        });

        Button signUpButton = (Button)findViewById(R.id.signUp);

        signUpButton.setTypeface(roboto);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Validation.isPhoneNumber(mobileNumber, true) && Validation.hasText(password)  && Validation.hasText(confirmPassword)
                        && Validation.matchPassword(password,confirmPassword)) {
                    User user = new User();
                    user.setAccountType(AccountType.MOBILE);
                    user.setMobile(mobileNumber.getText().toString());
                    user.setAccountId(mobileNumber.getText().toString());//temp fix
                    user.setPassword(password.getText().toString());
                    jsonRequest(user.toJson(), Constants.SIGNUP_REQUEST);
                }
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

//        ((TextView)findViewById(R.id.fillUpDetail)).setTypeface(roboto);
//
//        ((TextView)findViewById(R.id.connectWithUs)).setTypeface(roboto);

        ((TextView)findViewById(R.id.alreadyRegistered)).setTypeface(roboto);

        ((TextView)findViewById(R.id.dividerText)).setTypeface(roboto);
    }

    void backButton(){
        this.finish();
    }
    void debug(String message){
        System.out.println("Sign Up Screen >> " + message);
    }


    @Override
    public void confirm(String verificationText) {

        user.setOtp(verificationText);
        jsonRequest(user.toJson(), Constants.CONFIRM_OTP_REQUEST);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void resend() {

        jsonRequest(user.toJson(),Constants.SIGNUP_REQUEST);
    }
}
