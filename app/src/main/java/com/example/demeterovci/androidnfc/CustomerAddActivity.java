package com.example.demeterovci.androidnfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;

public class CustomerAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        Intent intent = getIntent();
        String id_card = intent.getStringExtra("id_card");

        if(id_card != null){
            TextView card_number_input = findViewById(R.id.card_number);
            card_number_input.setText(id_card);
            card_number_input.setFocusable(false);
        }
    }


    public void saveCustomer(View view) {
        TextView card_number_input = findViewById(R.id.card_number);
        EditText deposit_input = findViewById(R.id.deposit);
        String card_number = card_number_input.getText().toString();
        String depositStr = deposit_input.getText().toString();
        double deposit;

        try {
            deposit = Double.parseDouble(depositStr);

            Connection db = new Connection(this);
            Customer customer = new Customer();
            customer.setCard_id(card_number);
            customer.setMoney(deposit);

            db.addCustomer(customer);

            /* @todo Edit user */
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id_card", card_number);
            startActivity(intent);
            finish();
        }
        catch (Exception e){
            Toast.makeText(this, getString(R.string.credit_deposit_error), Toast.LENGTH_SHORT).show();
        }
    }
}
