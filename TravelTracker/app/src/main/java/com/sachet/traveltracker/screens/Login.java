package com.sachet.traveltracker.screens;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
//import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.beans.AccountType;
import com.sachet.traveltracker.beans.User;
import com.sachet.traveltracker.fragments.ForgetPasswordFragment;
import com.sachet.traveltracker.utils.Constants;
import com.sachet.traveltracker.utils.ErrorDialogue;
import com.sachet.traveltracker.utils.FacebookUtil;
import com.sachet.traveltracker.utils.FontType;
import com.sachet.traveltracker.utils.Fonts;
import com.sachet.traveltracker.utils.GoogleUtil;
import com.sachet.traveltracker.utils.ResponseCode;
import com.sachet.traveltracker.utils.Validation;
import com.sachet.traveltracker.utils.VolleyErrorHelper;
import com.sachet.traveltracker.utils.VolleySigleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,ForgetPasswordFragment.OnClickListener {

    CallbackManager callbackManager;
    ImageButton eyeButton;
    EditText password;
    EditText mobileNumber;
    GoogleApiClient mGoogleApiClient;
    String TAG = "Login Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActionBar();
        setViewObjects();
        mGoogleApiClient = GoogleUtil.init(this);
        setCallbackManager();
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

    private void googleSignIn() {
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
        }else{
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
            requestLogin(user);
//            requestLogin();
//            showToast(acct.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
            showToast(getString(R.string.authentication_failed));
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    void setCallbackManager(){
        callbackManager = FacebookUtil.getCallBackManager(this, new FacebookUtil.FacebookUtilCallback() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
//                Log.d(TAG, "On Success " + accessToken);
//                showToast(profile.getName());
                User user = new User();
                user.setAccountType(AccountType.FACEBOOK);
                user.setAccountId(profile.getId());
                user.setName(profile.getName());
                requestLogin(user);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "On onCancel");
                showToast(getString(R.string.login_cancelled));
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "On onError");
            }
        });
    }

    void setViewObjects(){

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

        TextView forgotPassword = (TextView)findViewById(R.id.forgotPassword);

        forgotPassword.setTypeface(roboto);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                debug("forgot passwords button");
                new ForgetPasswordFragment().show(getSupportFragmentManager(),getResources().getString(R.string.forgot_password));
            }
        });



        ImageButton googleButton = (ImageButton)findViewById(R.id.googleButton);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("google button");
                googleSignIn();
            }
        });


        ImageButton facebookButton = (ImageButton)findViewById(R.id.facebookButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug("facebook button");
                FacebookUtil.loginWithReadPermissions(Login.this);
            }
        });

        Button signInButton = (Button)findViewById(R.id.signIn);

        signInButton.setTypeface(roboto);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                debug("sign in button");
//                AlertDialog dialog = ErrorDialogue.getDialogue(Login.this, "Error Title", "This is a error dialogue message");

                if (Validation.isPhoneNumber(mobileNumber, true) && Validation.hasText(password)) {

                    debug("validated");
                    User user = new User();
                    user.setAccountType(AccountType.MOBILE);
                    user.setMobile(mobileNumber.getText().toString());
                    user.setAccountId(mobileNumber.getText().toString());//temp fix
                    user.setPassword(password.getText().toString());
                    requestLogin(user);
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

//        ((TextView)findViewById(R.id.welcomeText)).setTypeface(roboto);
//
//        ((TextView)findViewById(R.id.destinationText)).setTypeface(roboto);

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

    private void requestLogin(final User user) {

//        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.LOGIN_REQUEST,user.toJson(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        processLoginResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mTextView.setText(error.toString());
//                        System.out.println("Volley Error " + error.getMessage());
                        ErrorDialogue.getDialogue(Login.this,"Error", VolleyErrorHelper.getMessage(error, getBaseContext()));
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
        VolleySigleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        VolleySigleton.getInstance(getApplicationContext()).getRequestQueue().cancelAll(TAG);
    }
    private void processLoginResponse(JSONObject response){
        try{
           int responseCode = response.getInt("responseCode");
            switch (responseCode){

                case ResponseCode.SUCCESS:

                    User user = User.fromJson(response.getString("responseBody"));
                    if(response.getString("responseMessage").equals("NEW_USER")){
                        launchAdditionalInfoActivity(user);
                        return;
                    }
                    launchDashboard(user);

                    break;

                default:
                    ErrorDialogue.getDialogue(Login.this,"Login Error",response.getString("responseMessage"));
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
    public void send(final String mobileNumber) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobileNumber", mobileNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, Constants.VERIFY_MOBILE_REQUEST,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startResetPasswordActivity(mobileNumber);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorDialogue.getDialogue(Login.this, "Error", VolleyErrorHelper.getMessage(error, getBaseContext()));
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

    @Override
    public void cancel() {

    }

    private void startResetPasswordActivity(String mobileNumber){
        Intent intent = new Intent(getBaseContext(),ResetPassword.class);
        intent.putExtra("mobile_number",mobileNumber);
        startActivity(intent);

    }

}
