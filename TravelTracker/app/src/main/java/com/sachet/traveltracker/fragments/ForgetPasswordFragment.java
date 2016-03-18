package com.sachet.traveltracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sachet.traveltracker.R;
import com.sachet.traveltracker.utils.Validation;


public class ForgetPasswordFragment extends DialogFragment {
    private OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout


        View view = inflater.inflate(R.layout.fragment_forget_password, null);

        final EditText mobileNumber = (EditText)view.findViewById(R.id.mobileNumber);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.sendVerificationCode, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ForgetPasswordFragment.this.getDialog().cancel();
                        listener.cancel();
                    }
                });

        final AlertDialog alertDialog = builder.show();

//        alertDialog.setCanceledOnTouchOutside(false);

        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation.isPhoneNumber(mobileNumber, true)){

                    listener.send(mobileNumber.getText().toString());
                }
            }
        });


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
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }
    }
    public interface OnClickListener{
        public void send(String mobileNumber);
        public void cancel();
    }
}