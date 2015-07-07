package com.marcuspeacock.MyFirstApp.workoutlog2.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

public class TrackDialogFragment extends DialogFragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstances) {
//        View view = inflater.inflate(R.layout.track_dialog, container);
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.track_dialog)
                .setTitle("Track")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDialogListener myDialogListener  = (MyDialogListener) getActivity();
                        HashMap<String, String> values = new HashMap<String, String>();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel
                    }
                });
        return builder.create();
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        View view = View.inflate(R.layout.track_dialog, null);
//
////        final EditText input = new EditText(getActivity());
////        builder.setMessage("Test").setView(input)
////                .setPositiveButton("fire", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        // FIRE ZE MISSILES!
////                    }
////                })
////                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        // User cancelled the dialog
////                    }
////                });
////        // Create the AlertDialog object and return it
////        return builder.create();
//    }


}
