package com.sachet.traveltracker.utils;

/**
 * Created by lenovo on 05-03-2016.
 */
public class Constants {

    public static String DEVICE_ID;
    public static String GCM_ID;



    private static final String BASE_URL = "http://hiretestd-dhiretest.rhcloud.com/driverhire/mobile/";

    public static final String LOGIN_REQUEST = BASE_URL+"login";
    public static final String SIGNUP_REQUEST = BASE_URL+"signup";
    public static final String FORGET_PASSWORD_REQUEST = BASE_URL+"changepassword";
    public static final String CONFIRM_OTP_REQUEST = BASE_URL+"confirmotp";
    public static final String UPDATE_USER_REQUEST = BASE_URL+"updateuser";
    public static final String VERIFY_MOBILE_REQUEST = BASE_URL+"sendotp/mobilenumber";
}
