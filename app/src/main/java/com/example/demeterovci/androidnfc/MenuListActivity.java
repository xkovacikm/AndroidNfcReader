package com.example.demeterovci.androidnfc;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;
import com.example.demeterovci.androidnfc.db.Menu;

import java.util.List;

public class MenuListActivity extends ListActivity {

    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final ListView listView = getListView();

        Connection db = new Connection(this);
        List<Menu> values = db.getMenus();

        ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                Menu menu    = (Menu) listView.getItemAtPosition(position);
                Intent intent = new Intent(MenuListActivity.this, MenuEditActivity.class);

                intent.putExtra("id", String.valueOf( menu.getId() ) );
                intent.putExtra("name", menu.getName() );
                intent.putExtra("cost", String.valueOf( menu.getCost() ) );

                finish();
                startActivity(intent);

            }

        });
    }
}
