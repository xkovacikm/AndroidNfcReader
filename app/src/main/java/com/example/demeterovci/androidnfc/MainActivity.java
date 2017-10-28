package com.example.demeterovci.androidnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;
import com.example.demeterovci.androidnfc.db.Menu;

/**
 * @todo - niesu vypisy ci sa podarilo alebo nie. Ani osetrenia. Ak sa zadaju zle udaje, spadne appka
 * @note - strasne pomaly ide ta appka, asi sa niekde vytvara strasne vela instancii alebo tak - neviem ci moja chyba. Singleton na DB je spraveny
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test
        /*Connection db = new Connection(this);

        db.addCustomer( new Customer( 1, "absdjffbaefb", 0.0 ) );
        db.addMenu( new Menu( 3, "polievka", 5.0 ) );

        Customer customer = db.getCustomerById(1);*/

    }

    /**
     * OnClick callback - show customers
     * @param view
     */
    public void showCustomerList(View view){
        Intent intent = new Intent(this, CustomerListActivity.class);
        startActivity(intent);
    }

    /**
     * OnClick callback - show menus
     * @param view
     */
    public void showMenuList(View view){
        Intent intent = new Intent(this, MenuListActivity.class);
        startActivity(intent);
    }

    /**
     * OnClick callback - Add menu
     * @param view
     */
    public void showMenuAdd(View view){
        Intent intent = new Intent(this, MenuEditActivity.class);
        startActivity(intent);
    }
}
