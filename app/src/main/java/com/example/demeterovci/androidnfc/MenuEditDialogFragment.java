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
import android.widget.TextView;

import com.example.demeterovci.androidnfc.db.Connection;


/**
 * Created by maria on 30.11.17.
 */

public class MenuEditDialogFragment extends DialogFragment {

    private MenuEditDialogFragment.Listener callback;
    private String name;
    private String price;
    private Integer id;

    private TextView name_textview;
    private TextView price_textview;
    private Connection db = new Connection(getActivity());

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MenuEditDialogFragment.Listener) context;
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

        name = getArguments().getString("name");
        price = getArguments().getString("price");
        id=getArguments().getInt("id");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_menu, null);

        builder.setView(view)
                .setPositiveButton(R.string.save_button, new Editer())
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuEditDialogFragment.this.getDialog().cancel();
                    }
                });

        name_textview = view.findViewById(R.id.menu_name_input);
        price_textview = view.findViewById(R.id.menu_price_input);

        name_textview.setText(name);
        price_textview.setText(price);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface Listener{
        void onEdit(Integer id, String name, Float price);
    }

    public class Editer implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            callback.onEdit(id, name_textview.getText().toString(), Float.parseFloat(price_textview.getText().toString()));
        }
    }

}
