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

public class MenuBuyDialogFragment extends DialogFragment {

    private MenuBuyDialogFragment.Listener callback;
    private String name;
    private String price;

    private TextView name_textview;
    private TextView price_textview;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MenuBuyDialogFragment.Listener) context;
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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buy_menu, null);

        builder.setView(view)
                .setPositiveButton(R.string.buy_button, new MenuBuyDialogFragment.Buyer())
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuBuyDialogFragment.this.getDialog().cancel();
                    }
                });

        name_textview = view.findViewById(R.id.menu_name_textview);
        price_textview = view.findViewById(R.id.menu_name_textview);

        name_textview.setText(name);
        price_textview.setText(price + "€");
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public interface Listener{
        void onBuy(Float paid_price);
    }

    public class Buyer implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            callback.onBuy(Float.parseFloat(price));
        }
    }
}
