package com.example.demeterovci.androidnfc;




import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
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
public class MainActivity extends AppCompatActivity implements Listener, MenuEditDialogFragment.Listener, MenuAddDialogFragment.Listener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private NfcAdapter mNfcAdapter;
    private Connection db = new Connection(this);
    private ListView foodList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private Integer selPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        String id_card = intent.getStringExtra("id_card");
        Customer customer = db.getCustomerByCardId(id_card);

        TextView cardNummeroText = findViewById(R.id.cardNummeroText);
        TextView creditText = findViewById(R.id.creditText);

        cardNummeroText.setText(customer.getCard_id());
        creditText.setText(customer.getMoney() + "€");

        //db.addMenu(new Menu(1, "Dobrota od mamky", 1.30));
        //foodList = findViewById(R.id.foodList);
        List<Menu> jedla = db.getMenus();

        myAdapter = new MyAdapter(new MyAdapterListener(), jedla);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*myAdapter.add();

        ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        foodList.setAdapter(adapter);*/
    }

    @Override
    public void onEdit(Menu menu) {
        db.updateMenu(menu);
        myAdapter.edit(selPos, menu);
    }

    @Override
    public void onAdd(Menu new_menu) {
        db.addMenu(new_menu);
        List<Menu> menus = db.getMenus();
        myAdapter.add(menus.get(menus.size()-1));
    }

    public void addMenu(View view) {
        showAddDialog();
    }

    private class MyAdapterListener implements MyAdapter.Listener{
        @Override
        public void onSelected(Integer data) {

        }

        @Override
        public void onDelete(Integer data) {
            Menu menu = db.getMenuById(data);
            db.deleteMenu(menu);
        }

        @Override
        public void onEdit(Integer id, Integer selectedPosition) {
            Log.d("myTag", "on edit listener");
            selPos = selectedPosition;
            showEditDialog(id);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    void showEditDialog(Integer id) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Menu tempmenu = db.getMenuById(id);

        Bundle bundle = new Bundle();
        bundle.putString("name", tempmenu.getName());
        bundle.putString("price", tempmenu.getCost() + "");
        bundle.putInt("id", tempmenu.getId());

        // Create and show the dialog.
        DialogFragment newFragment = new MenuEditDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(ft,"dialog");

       /* newFragment.getDialog().findViewById(R.id.menu_name_input);

        TextView name_textview = newFragment.getDialog().findViewById(R.id.menu_name_input);
        TextView price_textview = newFragment.getDialog().findViewById(R.id.menu_price_input);

        name_textview.setText(tempmenu.getName());
        price_textview.setText(tempmenu.getCost() + "");*/

    }

    void showAddDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("add_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new MenuAddDialogFragment();
        newFragment.show(ft,"add_dialog");
    }

    @Override
    public void onDialogDisplayed() {

    }

    @Override
    public void onDialogDismissed() {

    }

}
