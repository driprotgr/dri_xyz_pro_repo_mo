package com.sachet.traveltracker.utils;

/**
 * Created by lenovo on 07-02-2016.
 */
import java.util.regex.Pattern;

import android.util.Patterns;
import android.widget.EditText;

/*
ArrayList<Boolean> formValidation = new ArrayList<Boolean>();

formValidation.add(FormValidation.validationSetError(EDITTEXT_NAME.getText().toString(), FormValidation.NAME, EDITTEXT_NAME));
formValidation.add(FormValidation.validationSetError(EDITTEXT_PASSWORD.getText().toString(), FormValidation.PW, EDITTEXT_PASSWORD));
formValidation.add(FormValidation.validationSetError(EDITTEXT_EMAIL.getText().toString(), FormValidation.EMAIL_COMPULSORY, EDITTEXT_EMAIL));

//IF validation passes all fields
if (!formValidation.contains(false)) {
    //DO SOMETHING
}
 */


public class FormValidation {

    public static final int NAME = 0;
    public static final int IC = 1;
    public static final int PW = 2;
    public static final int CFM_PW = 3;
    public static final int EMAIL_COMPULSORY = 4;
    public static final int EMAIL = 5;
    public static final int PHONE_COMPULSORY = 6;
    public static final int PHONE = 7;
    public static final int POSTAL_COMPULSORY = 8;
    public static final int POSTAL = 9;
    public static final int HOME_UNIT_COMPULSORY = 10;
    public static final int DATE_COMPULSORY = 11;
    public static final int DATE = 12;
    public static final int CASH_AMOUNT_COMPULSORY = 13;
    public static final int CASH_AMOUNT = 14;

    public static String validate(String validate_string, int code) {

        String errorMsg = "";
        Pattern strPattern;
        final String postal_regex = "\\d{6}";
        final String dob_regex ="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$";
        final String cash_regex = "^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{2})?|(?:,[0-9]{3})*(?:\\.[0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)$";

        switch(code) {
            case NAME:
                if(validate_string.equals(""))
                    errorMsg = "Please enter your name to proceed.";
                break;
            case IC:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a valid IC No. to proceed";
                break;
            case PW:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a valid password to proceed.";
                break;
            case CFM_PW:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a valid password to proceed.";
                break;
            case EMAIL_COMPULSORY:
                if(validate_string.equals(""))
                    errorMsg = "Please enter an Email Address to proceed.";
                else {
                    strPattern = Patterns.EMAIL_ADDRESS;
                    if(!strPattern.matcher(validate_string).matches())
                        errorMsg = "Invalid Email address entered. Please try again.";
                }
                break;
            case EMAIL:
                if(!validate_string.equals("")) {
                    strPattern = Patterns.EMAIL_ADDRESS;
                    if(!strPattern.matcher(validate_string).matches())
                        errorMsg = "Invalid Email address entered. Please try again.";
                }
                break;
            case PHONE_COMPULSORY:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a phone number to proceed.";
                else {
                    strPattern = Patterns.PHONE;
                    if(!strPattern.matcher(validate_string).matches())
                        errorMsg = "Invalid Phone number entered. Please try again.";
                }
                break;
            case PHONE:
                if(!validate_string.equals("")) {
                    strPattern = Patterns.PHONE;
                    if(!strPattern.matcher(validate_string).matches())
                        errorMsg = "Invalid Phone number entered. Please try again.";
                }
                break;
            case POSTAL_COMPULSORY:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a postal address to proceed.";
                else {
                    if (!Pattern.matches(postal_regex, validate_string))
                        errorMsg = "Invalid postal address entered. Please try again.";
                }
                break;
            case POSTAL:
                if(!validate_string.equals("")) {
                    if (!Pattern.matches(postal_regex, validate_string))
                        errorMsg = "Invalid postal address entered. Please try again.";
                }
                break;
            case HOME_UNIT_COMPULSORY:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a unit number to proceed.";
                break;
            case DATE_COMPULSORY:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a date to proceed.";
                else {
                    if (!Pattern.matches(dob_regex, validate_string))
                        errorMsg = "Invalid date entered. Please try again.";
                }
                break;
            case DATE:
                if(!validate_string.equals(""))
                    if (!Pattern.matches(dob_regex, validate_string))
                        errorMsg = "Invalid date entered. Please try again.";
                break;
            case CASH_AMOUNT_COMPULSORY:
                if(!validate_string.equals(""))
                    if (!Pattern.matches(cash_regex, validate_string))
                        errorMsg = "Invalid amount entered. Please try again.";
                break;
            case CASH_AMOUNT:
                if(validate_string.equals(""))
                    errorMsg = "Please enter a valid amount to proceed";
                else {
                    if (!Pattern.matches(cash_regex, validate_string))
                        errorMsg = "Invalid amount entered. Please try again.";
                }
                break;
        }

        return errorMsg;
    }

    public static String validate_compulsory(String validate_string, String regex, String error_blank, String error_extra) {

        String errorMsg = "";
//        Pattern strPattern;

        if(validate_string.equals(""))
            errorMsg = error_blank;
        else {
            if (!Pattern.matches(regex, validate_string))
                errorMsg = error_extra;
        }
//        break;

        return errorMsg;
    }


    public static boolean validationSetError(String input, int type, EditText et_set) {
        String validateError = FormValidation.validate(input, type);
        if(!validateError.equals("")) {
            et_set.setError(validateError);
            return false;
        } else {
            et_set.setError(null);
            return true;
        }
    }

    public static String validate(String validate_string, String regex, String error_extra) {

        String errorMsg = "";
        Pattern strPattern;

        if (!Pattern.matches(regex, validate_string)){
            errorMsg = error_extra;
        }
//      break;

        return errorMsg;
    }
    public static boolean validationSetErrorCustom(String input, String regex, String error_extra, EditText et_set) {
        String validateError = FormValidation.validate(input, regex, error_extra);
        if(!validateError.equals("")) {
            et_set.setError(validateError);
            return false;
        } else {
            et_set.setError(null);
            return true;
        }
    }

    public static boolean validationSetErrorCustomCompulsory(String input, String regex, String error_blank, String error_extra, EditText et_set) {
        String validateError = FormValidation.validate_compulsory(input, regex, error_blank, error_extra);
        if(!validateError.equals("")) {
            et_set.setError(validateError);
            return false;
        } else {
            et_set.setError(null);
            return true;
        }
    }


}