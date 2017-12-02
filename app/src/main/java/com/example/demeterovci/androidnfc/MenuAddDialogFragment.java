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

import com.example.demeterovci.androidnfc.db.Menu;

public class MenuAddDialogFragment extends DialogFragment {

    private MenuAddDialogFragment.Listener callback;

    private TextView name_textview;
    private TextView price_textview;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MenuAddDialogFragment.Listener) context;
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
        View view = inflater.inflate(R.layout.dialog_add_menu, null);

        builder.setView(view)
                .setPositiveButton(R.string.save_button, new MenuAddDialogFragment.Adder())
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuAddDialogFragment.this.getDialog().cancel();
                    }
                });

        name_textview = view.findViewById(R.id.menu_name_input);
        price_textview = view.findViewById(R.id.menu_price_input);

        // Create the AlertDialog object and return it
        return builder.create();
    }


    public interface Listener{
        void onAdd(Menu new_menu);
    }

    public class Adder implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String jedlo_name = name_textview.getText().toString();
            String jedlo_price = price_textview.getText().toString();

            if(jedlo_name.isEmpty()){
                jedlo_name = "jedlo";
            }

            if(jedlo_price.isEmpty()){
                jedlo_price = "0";
            }

            Menu new_menu = new Menu(jedlo_name, Float.parseFloat(jedlo_price));
            callback.onAdd(new_menu);
        }
    }
}
