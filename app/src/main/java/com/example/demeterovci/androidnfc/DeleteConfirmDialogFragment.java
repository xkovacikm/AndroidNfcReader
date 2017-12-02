package com.example.demeterovci.androidnfc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DeleteConfirmDialogFragment extends DialogFragment {
    private DeleteConfirmDialogFragment.Listener callback;
    private Integer idDB;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DeleteConfirmDialogFragment.Listener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        idDB = getArguments().getInt("id");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_confirm)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {callback.onDelete(idDB,true);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface Listener{
        void onDelete(Integer dbID, boolean d);
    }
}
