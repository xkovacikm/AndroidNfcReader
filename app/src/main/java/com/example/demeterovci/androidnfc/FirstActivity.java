package com.example.demeterovci.androidnfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.demeterovci.androidnfc.db.Connection;
import com.example.demeterovci.androidnfc.db.Customer;

import java.util.Arrays;

public class FirstActivity extends AppCompatActivity {
    private NfcAdapter mNfcAdapter;
    private Connection db = new Connection(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        createAndmin();
        initNFC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        //Log.d(TAG, "onNewIntent: "+intent.getAction());

        if(tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            String id_card = bytearray2intarray(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

            if(id_card != null){
                Customer customer = this.db.getCustomerByCardId(id_card);

                if(customer.getCard_id() != null){
                    /* @todo Vyber menu alebo editacia alebo vyber menu? Neviem aky chceme flow appky */
                    Intent showOffer = new Intent(this, MainActivity.class);

                    showOffer.putExtra("id_card", id_card);
                    startActivity(showOffer);
                }
                else{
                    /*Start CustomerAddActivity*/
                    Intent customerAdd = new Intent(this, CustomerAddActivity.class);
                    customerAdd.putExtra("id_card", id_card);
                    startActivity(customerAdd);

                }
            }
            else{
                Toast.makeText(this, getString(R.string.empty_card_number), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createAndmin(){
        if(db.getCustomers().size() == 0){
            Connection db = new Connection(this);
            Customer customer = new Customer();
            customer.setCard_id("0");
            customer.setMoney(10000);
            db.addCustomer(customer);
        }

    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    String ByteArrayToHexString(byte [] inarray)
    {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }


    public String bytearray2intarray(byte[] barray)
    {
        int[] iarray = new int[barray.length];
        int i = 0;
        for (byte b : barray)
            iarray[i++] = b & 0xff;
        return Arrays.toString(iarray).replace("[", "").replace("]", "").replace(", ", "");
    }

    public void loginAsAdmin(View view) {
        Intent showOffer = new Intent(this, MainActivity.class);

        showOffer.putExtra("id_card", "0");
        startActivity(showOffer);
    }
}
