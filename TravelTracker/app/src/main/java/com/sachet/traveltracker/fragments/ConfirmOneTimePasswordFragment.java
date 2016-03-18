package com.sachet.traveltracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sachet.traveltracker.R;

/**
 * Created by lenovo on 11-03-2016.
 */
public class ConfirmOneTimePasswordFragment extends DialogFragment {
    private OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout


        View view = inflater.inflate(R.layout.confirm_otp_dialog, null);

        final EditText verificationText = (EditText)view.findViewById(R.id.verificationText);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirm,null)
//                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // sign in the user ...
//                        listener.confirm(verificationText.getText().toString());
//                    }
//                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmOneTimePasswordFragment.this.getDialog().cancel();
                        listener.cancel();
                    }
                }).setNeutralButton(R.string.resend, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.resend();
                    }
                });

        final TextView timer = (TextView)view.findViewById(R.id.timer);

        final AlertDialog alertDialog = builder.show();

        alertDialog.setCanceledOnTouchOutside(false);

        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = verificationText.getText().toString();

                if (text == null || text.isEmpty())
                    return;

                listener.confirm(text);
            }
        });

        final int thousand = 1000;
        new CountDownTimer(thousand * 60 * 6, thousand) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = String.format("%02d:%02d",  (millisUntilFinished / (60 * thousand)), ((millisUntilFinished % (60 * thousand)) / thousand));

                timer.setText(time);
//                timer.setText('0'+(millisUntilFinished / (60 * thousand)) + ":" + ((millisUntilFinished % (60 * thousand)) / thousand));
            }

            @Override
            public void onFinish() {
                timer.setText("Please resend");
            }
        }.start();
        return alertDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickListener) {
            listener = (OnClickListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnClickListener");
        }
    }
    public interface OnClickListener{
        public void confirm(String verificationText);
        public void cancel();
        public void resend();
    }
}
