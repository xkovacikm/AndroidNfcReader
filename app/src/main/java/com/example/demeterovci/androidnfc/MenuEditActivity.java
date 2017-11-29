package com.example.demeterovci.androidnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Menu;

public class MenuEditActivity extends AppCompatActivity {

    private boolean newCustomer = true;

    EditText id;
    EditText name;
    EditText cost;

    private Connection db = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);

        Intent intent = getIntent();

        if( intent.hasExtra("id") == true && intent.hasExtra("name") == true && intent.hasExtra("cost") == true ){

            _setMenuFields();

            newCustomer = false;

            name.setText( intent.getStringExtra("name") );
            cost.setText( String.valueOf( intent.getStringExtra("cost" ) ) );

        } else {
            Button deleteButton = (Button) findViewById( R.id.menu_delete );
            deleteButton.setClickable( false );
            deleteButton.setVisibility( View.INVISIBLE );
        }
    }

    /**
     * OnClick callback - Save menu button
     * @param view
     */
    public void editOrAddMenu(View view){

        _setMenuFields();

        if( newCustomer ){
            db.addMenu( new Menu( Integer.parseInt( String.valueOf( id.getText() ) ),
                                String.valueOf( name.getText() ),
                                Double.parseDouble( String.valueOf( cost.getText() ) )
            ) );

        } else {
            db.updateMenu( new Menu( Integer.parseInt( String.valueOf( id.getText() ) ),
                                String.valueOf( name.getText() ),
                                Double.parseDouble( String.valueOf( cost.getText() ) )
            ) );
        }

        Intent intent = new Intent(this, MenuListActivity.class);
        finish();
        startActivity(intent);

    }

    /**
     * OnClick callback - delete menu button
     * @param view
     */
    public void deleteMenu(View view){
        _setMenuFields();

        if( !newCustomer ){
            db.deleteMenu(
                    new Menu(
                            Integer.parseInt( String.valueOf( id.getText() ) ),
                            String.valueOf( name.getText() ),
                            Double.parseDouble( String.valueOf( cost.getText() ) )
                    )
            );
        }

        Intent intent = new Intent(this, MenuListActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * Set's EditViews fields
     */
    private void _setMenuFields(){
        id = (EditText) findViewById(R.id.menu_id);
        name = (EditText) findViewById(R.id.menu_name);
        cost = (EditText) findViewById(R.id.menu_cost);
    }
}
