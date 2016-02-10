package com.sachet.traveltracker.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by lenovo on 07-02-2016.
 */


public class Fonts {

    private static final String basePath = "fonts/";

    public static Typeface getFont(Context context, FontType type){

        String fontPathName = null;

        switch (type){
            case DEFAULT:
                fontPathName = "Roboto-Black.ttf";
                break;

            case BLACK_ITALIC:
                fontPathName = "Roboto-BlackItalic.ttf";
                break;

            case BOLD:
                fontPathName = "Roboto-Bold.ttf";
                break;

            case BOLD_ITALIC:
                fontPathName = "Roboto-BoldItalic.ttf";
                break;

            case ITALIC:
                fontPathName = "Roboto-Italic.ttf";
                break;

            case LIGHT:
                fontPathName = "Roboto-Light.ttf";
                break;

            case MEDIUM:
                fontPathName = "Roboto-Medium.ttf";
                break;

            case MEDIUM_ITALIC:
                fontPathName = "Roboto-MediumItalic.ttf";
                break;

            case REGULAR:
                fontPathName = "Roboto-Regular.ttf";
                break;

            case THIN:
                fontPathName = "Roboto-Thin.ttf";
                break;

            case THIN_ITALIC:
                fontPathName = "Roboto-ThinItalic.ttf";
                break;

            case CONDENSED_BOLD:
                fontPathName = "RobotoCondensed-Bold.ttf";
                break;

            case CONDENSED_BOLD_ITALIC:
                fontPathName = "RobotoCondensed-BoldItalic.ttf";
                break;

            case CONDENSED_ITALIC:
                fontPathName = "RobotoCondensed-Italic.ttf";
                break;

            case CONDENSED_LIGHT:
                fontPathName = "RobotoCondensed-Light.ttf";
                break;

            case CONDENSED_LIGHT_ITALIC:
                fontPathName = "RobotoCondensed-LightItalic.ttf";
                break;

            case CONDENSED_REGULAR:
                fontPathName = "RobotoCondensed-Regular.ttf";
                break;
        }
        return Typeface.createFromAsset(context.getAssets(), basePath + fontPathName);
    }
}
