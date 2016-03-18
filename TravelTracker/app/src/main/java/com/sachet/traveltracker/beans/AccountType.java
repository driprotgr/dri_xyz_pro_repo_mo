package com.sachet.traveltracker.beans;

/**
 * Created by lenovo on 05-03-2016.
 */

public enum AccountType {
    FACEBOOK("F"), GOOGLE("G"), MOBILE("M");
    private String value;

    AccountType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public static AccountType fromString(String value) {
        if (value != null) {
            for (AccountType objType : AccountType.values()) {
                if (value.equalsIgnoreCase(objType.value)) {
                    return objType;
                }
            }
        }
        return null;
    }
}