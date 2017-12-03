package com.example.demeterovci.androidnfc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CreditAddDialogFragment extends DialogFragment {

    private CreditAddDialogFragment.Listener callback;
    private EditText credit_edit_text;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CreditAddDialogFragment.Listener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.credit_add_dialog, null);

        builder.setView(view)
                .setPositiveButton(R.string.recharge_button, new CreditAddDialogFragment.CreditRecharger())
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreditAddDialogFragment.this.getDialog().cancel();
                    }
                });

        credit_edit_text = view.findViewById(R.id.credit_edit_text);
        //credit_edit_text.setText("10");
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public interface Listener{
        void onRecharge(Float paid_price);
    }

    public class CreditRecharger implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            callback.onRecharge(Float.parseFloat(credit_edit_text.getText().toString()));
        }
    }
}
