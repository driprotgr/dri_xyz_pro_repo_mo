package com.sachet.traveltracker.utils;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

import com.sachet.traveltracker.R;

/**
 * Created by lenovo on 07-02-2016.
 */
public class ErrorDialogue {

    public static AlertDialog getDialogue(Context context,String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(R.string.ok_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        }).create();
        alertDialog.show();
       /* Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setBackgroundColor(context.getResources().getColor(R.color.skyBlueDark));
        positiveButton.setTextColor(Color.WHITE);*/
        return alertDialog;
    }
/*
    public static AlertDialog getDialogue(Context context,String title,String message,String postitiveBtnText,String negativeBtnText){
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(postitiveBtnText, new DialogInterface.OnClickListener() {
            @Override1
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        }).setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.dismiss();
            }
        }).create();
        return alertDialog;
    }*/
}
