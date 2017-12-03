package com.example.demeterovci.androidnfc;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;
import com.example.demeterovci.androidnfc.db.Menu;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Listener, MenuEditDialogFragment.Listener, MenuAddDialogFragment.Listener, DeleteConfirmDialogFragment.Listener, MenuBuyDialogFragment.Listener, CreditAddDialogFragment.Listener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private NfcAdapter mNfcAdapter;
    private Connection db = new Connection(this);
    private ListView foodList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private Integer selPos;
    private Customer customer;
    private TextView creditText;

    private boolean admin;

    private final int delayTime = 10000;
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String id_card = intent.getStringExtra("id_card");
        admin = intent.getBooleanExtra("is_admin", false);
        if(!admin)
        {
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            //toolbar.getRootView().findViewById(R.id.actoin_customer_list).setVisibility(View.INVISIBLE);
        }


        customer = db.getCustomerByCardId(id_card);

        TextView cardNummeroText = findViewById(R.id.cardNummeroText);
        creditText = findViewById(R.id.creditText);

        cardNummeroText.setText(customer.getCard_id());
        creditText.setText(String.format(Locale.US,"%.2f", customer.getMoney()) + "€");

        //db.addMenu(new Menu(1, "Dobrota od mamky", 1.30));
        //foodList = findViewById(R.id.foodList);
        List<Menu> jedla = db.getMenus();

        myAdapter = new MyAdapter(new MyAdapterListener(), jedla, admin);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*myAdapter.add();

        ArrayAdapter<Menu> adapter = new ArrayAdapter<Menu>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        foodList.setAdapter(adapter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(db.getCustomerByCardId(customer.getCard_id()).getCard_id() == null){
            Toast.makeText(this, getString(R.string.onlogout), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FirstActivity.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            this.finish();
        }
        else{
            customer = db.getCustomerByCardId(customer.getCard_id());
            creditText.setText(String.format(Locale.US,"%.2f", customer.getMoney()) + "€");
        }
    }

    @Override
    public void onEdit(Menu menu) {
        db.updateMenu(menu);
        myAdapter.edit(selPos, menu);
        Toast.makeText(this, getString(R.string.onedit), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdd(Menu new_menu) {
        db.addMenu(new_menu);
        List<Menu> menus = db.getMenus();
        myAdapter.add(menus.get(menus.size()-1));
        Toast.makeText(this, getString(R.string.onadd), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(Integer dbID, boolean d) {
        if(d){
            Menu menu = db.getMenuById(dbID);
            db.deleteMenu(menu);
            myAdapter.delete(selPos);
            Toast.makeText(this, getString(R.string.ondelete), Toast.LENGTH_SHORT).show();
        }
    }

    public void addMenu(View view) {
        showAddDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        menu.findItem(R.id.actoin_customer_list).setVisible(admin);
        super.onPrepareOptionsMenu(menu);
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

    void showDeleteConfirmDialog(Integer dbID){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("delete_confirm_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Bundle bundle = new Bundle();
        bundle.putInt("id", dbID);

        // Create and show the dialog.
        DialogFragment newFragment = new DeleteConfirmDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(ft,"delete_confirm_dialog");
    }

    void showBuyDialog(Integer id){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("buy_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Menu tempmenu = db.getMenuById(id);

        Bundle bundle = new Bundle();
        bundle.putString("name", tempmenu.getName());
        bundle.putString("price", tempmenu.getCost() + "");
        //bundle.putInt("id", tempmenu.getId());

        // Create and show the dialog.
        DialogFragment newFragment = new MenuBuyDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(ft,"buy_dialog");
    }

    @Override
    public void onDialogDisplayed() {

    }

    @Override
    public void onDialogDismissed() {

    }

    public void addCredit(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("add_credit_dialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new CreditAddDialogFragment();
        newFragment.show(ft,"add_credit_dialog");
    }

    public void logout(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("logout_dialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new LogoutConfirmDialogFragment();
        newFragment.show(ft,"logout_dialog");
    }

    @Override
    public void onBuy(Float paid_price) {
        customer.setMoney(customer.getMoney() - paid_price);
        db.updateCustomer(customer);
        creditText.setText(String.format(Locale.US,"%.2f", customer.getMoney()) + "€");
        Toast.makeText(this, getString(R.string.onbuy), Toast.LENGTH_SHORT).show();
    }

    public void showCustomerList(MenuItem item) {
        Intent intent = new Intent(this, CustomerListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRecharge(Float paid_price) {
        customer.setMoney(customer.getMoney() + paid_price);
        db.updateCustomer(customer);
        creditText.setText(String.format(Locale.US,"%.2f", customer.getMoney()) + "€");
        Toast.makeText(this, getString(R.string.onrecharge), Toast.LENGTH_SHORT).show();
    }

    //Closes activity after 10 seconds of inactivity
    public void onUserInteraction(){
        myHandler.removeCallbacks(closeControls);
        myHandler.postDelayed(closeControls, delayTime);
    }

    private Runnable closeControls = new Runnable() {
        public void run() {
            finish();
        }
    };

    private class MyAdapterListener implements MyAdapter.Listener{
        @Override
        public void onSelected(Integer id) {

            if(db.getMenuById(id).getCost() <= customer.getMoney()) {
                showBuyDialog(id);
            }
            else{
                Toast.makeText(getApplicationContext(), "Nízky kredit na účte!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onDelete(Integer dbID, Integer selectedPosition) {
            selPos = selectedPosition;
            showDeleteConfirmDialog(dbID);

        }

        @Override
        public void onEdit(Integer id, Integer selectedPosition) {
            Log.d("myTag", "on edit listener");
            selPos = selectedPosition;
            showEditDialog(id);
        }
    }
}

