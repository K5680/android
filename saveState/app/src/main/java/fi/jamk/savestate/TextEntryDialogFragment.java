package fi.jamk.savestate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by k5680 on 25.9.2017.
 */

public class TextEntryDialogFragment extends DialogFragment{

    //send data to host
    public interface TextEntryDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String text);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // use this instance to deliver action events
    TextEntryDialogListener mListener;

    // override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        //verify that the host activity implements the callback interface
        try {
            mListener = (TextEntryDialogListener) activity;
        } catch (ClassCastException e) {
            // the activity doesnt implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement TextEntryDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //new alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //inflate & set layout
        // pass null as parent view
        final View dialogView = inflater.inflate(R.layout.textentry_dialog, null);
        builder.setView(dialogView)
                .setTitle("Give a new text")
                //add buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //find team name
                        EditText editText = (EditText)
                                dialogView.findViewById(R.id.editText);
                        String text = editText.getText().toString();
                        //send positive button event to host activity
                        mListener.onDialogPositiveClick(TextEntryDialogFragment.this, text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //send neg button
                        mListener.onDialogNegativeClick(TextEntryDialogFragment.this);
                    }
                });
        return builder.create();
    }

}

