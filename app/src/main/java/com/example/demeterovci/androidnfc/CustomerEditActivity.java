package com.example.demeterovci.androidnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;

public class CustomerEditActivity extends AppCompatActivity {

    private boolean newCustomer = true;

    EditText id;
    EditText card_id;
    EditText money;

    private Connection db = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);

        Intent intent = getIntent();

        if( intent.hasExtra("id") == true && intent.hasExtra("card_id") == true && intent.hasExtra("money") == true ){

            _setCustomerFields();
            newCustomer = false;

            id.setText( intent.getStringExtra("id" ) );
            card_id.setText( intent.getStringExtra("card_id") );
            money.setText( String.valueOf( intent.getStringExtra("money" ) ) );

        } else {
            // daco zle, tu nema byt
        }
    }

    /**
     * OnClick callback - save customer button
     * @param view
     */
    public void editCustomer(View view){
        _setCustomerFields();
        
        db.updateCustomer(
                new Customer(
                    Integer.parseInt( String.valueOf( id.getText() ) ),
                    ( String.valueOf( card_id.getText() ) ),
                    Double.parseDouble( String.valueOf( money.getText() ) )
                )
        );

        Intent intent = new Intent(this, CustomerListActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * OnClick callback - delete customer
     * @param view
     */
    public void deleteCustomer(View view){
        _setCustomerFields();

        db.deleteCustomer(
                new Customer(
                        Integer.parseInt( String.valueOf( id.getText() ) ),
                        ( String.valueOf( id.getText() ) ),
                        Double.parseDouble( String.valueOf( id.getText() ) )
                )
        );

        Intent intent = new Intent(this, CustomerListActivity.class);
        finish();
        startActivity(intent);
    }



    /**
     * Set's EditViews fields
     */
    private void _setCustomerFields(){
        id = (EditText) findViewById(R.id.customer_id);
        card_id = (EditText) findViewById(R.id.customer_card_id);
        money = (EditText) findViewById(R.id.customer_money);
    }
}
