package com.example.demeterovci.androidnfc;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demeterovci.androidnfc.db.Menu;

import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private static final String TAG = "TAG (adapter)";
    private List<Menu> data;
    private Listener listener;
    private int selectedPosition;
    private boolean admin;

    public MyAdapter(Listener listener, List<Menu> jedla, boolean admin) {
        this.listener = listener;
        this.data = jedla;
        this.admin = admin;
    }

    public void add(Menu menu){
        data.add(menu);
        notifyItemInserted(data.indexOf(menu));
    }

    public void edit(Integer position, Menu menu){
        data.set(position,menu);
        notifyItemChanged(position);
    }

    public void delete(Integer position){
        int mypos = position;
        data.remove(mypos);
        Log.i("fungus", position+"");
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.textView.setText(data.get(position).getName());
        holder.priceView.setText(String.format(Locale.US, "%.2f", data.get(position).getCost()) + "€");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface Listener{
        void onSelected(Integer string);
        void onDelete(Integer string, Integer selectedPosition);
        void onEdit(Integer id, Integer selectedPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView priceView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            priceView = itemView.findViewById(R.id.item_price);
            itemView.setOnClickListener(new SelectSender());
            if(admin){
                itemView.findViewById(R.id.remove_item).setOnClickListener(new Deleter());
                itemView.findViewById(R.id.edit_item).setOnClickListener(new Editer());
            }
            else{
                itemView.findViewById(R.id.remove_item).setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.edit_item).setVisibility(View.INVISIBLE);
            }



        }

        private class SelectSender implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                selectedPosition = getLayoutPosition();
                listener.onSelected(data.get(selectedPosition).getId());
                Log.i(TAG, "selected: " + data.get(selectedPosition));
            }
        }

        public class Deleter implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                selectedPosition = getLayoutPosition();
                if(selectedPosition < 0 )
                    return;
                Log.i(TAG, "delete: " + data.get(selectedPosition));
                listener.onDelete(data.get(selectedPosition).getId(), selectedPosition);
            }
        }

        public class Editer implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                selectedPosition = getLayoutPosition();
                Log.i(TAG, "edit: " + data.get(selectedPosition));
                listener.onEdit(data.get(selectedPosition).getId(), selectedPosition);
            }
        }
    }

}