package com.example.demeterovci.androidnfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;
import com.example.demeterovci.androidnfc.db.Menu;

import java.util.List;

/**
 * @todo - niesu vypisy ci sa podarilo alebo nie. Ani osetrenia. Ak sa zadaju zle udaje, spadne appka
 * @note - strasne pomaly ide ta appka, asi sa niekde vytvara strasne vela instancii alebo tak - neviem ci moja chyba. Singleton na DB je spraveny
 */
public class MainActivity extends AppCompatActivity implements Listener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private NfcAdapter mNfcAdapter;
    private Connection db = new Connection(this);
    private ListView foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id_card = intent.getStringExtra("id_card");
        Customer customer = db.getCustomerByCardId(id_card);

        TextView cardNummeroText = findViewById(R.id.cardNummeroText);
        TextView creditText = findViewById(R.id.creditText);

        cardNummeroText.setText(customer.getCard_id());
        creditText.setText(customer.getMoney() + "€");
        foodList = findViewById(R.id.foodList);



        db.addMenu(new Menu(1, "Dobrota od mamky", 1.30));

        List<Menu> values = db.getMenus();

        ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        foodList.setAdapter(adapter);
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

    @Override
    public void onDialogDisplayed() {

    }

    @Override
    public void onDialogDismissed() {

    }

}
