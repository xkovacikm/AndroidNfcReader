package com.example.demeterovci.androidnfc;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;

import java.util.List;

public class CustomerListActivity extends ListActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final ListView listView = getListView();

        Connection db = new Connection(this);
        List<Customer> values = db.getCustomers();

        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                Customer  customer    = (Customer) listView.getItemAtPosition(position);
                Intent intent = new Intent(CustomerListActivity.this, CustomerEditActivity.class);

                intent.putExtra("id", String.valueOf( customer.getId() ) );
                intent.putExtra("card_id", customer.getCard_id() );
                intent.putExtra("money", String.valueOf( customer.getMoney() ) );

                finish();
                startActivity(intent);

            }

        });
    }
}
