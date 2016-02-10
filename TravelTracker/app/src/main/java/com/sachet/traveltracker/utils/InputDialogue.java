package com.sachet.traveltracker.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sachet.traveltracker.R;

/**
 * Created by lenovo on 09-02-2016.
 */
public class InputDialogue {

    public static void createInputDialogue(Context context,String title, final UserActionListener userActionListener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

// Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("OK",null);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = builder.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        positiveButton.setBackgroundColor(context.getResources().getColor(R.color.skyBlueDark));
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Do something
                //Dismiss once everything is OK.
                String m_Text = input.getText().toString();
                Log.d("setOnShowListener", "setOnShowListener " + m_Text);
                if (m_Text == null || m_Text.isEmpty())
                    return;
                userActionListener.onPositiveAction(input.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    public interface UserActionListener{
        public void onPositiveAction(String text);
    }
}
