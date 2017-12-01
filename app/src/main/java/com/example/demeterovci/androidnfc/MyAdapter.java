package com.example.demeterovci.androidnfc;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demeterovci.androidnfc.db.Menu;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Menu> data;
    private static final String TAG = "TAG (adapter)";
    private Listener listener;
    private int selectedPosition;

    public MyAdapter(Listener listener, List<Menu> jedla) {
        this.listener = listener;
        this.data = jedla;
    }

    public interface Listener{
        void onSelected(Integer string);
        void onDelete(Integer string);
        void onEdit(Integer id, Integer selectedPosition);
    }

    public void add(Menu menu){
        data.add(menu);
        notifyItemInserted(data.indexOf(menu));
    }

    public void edit(Integer position, Menu menu){
        data.set(position,menu);
        notifyItemChanged(position);
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
        holder.priceView.setText(String.format("%.2f",data.get(position).getCost()) + "€");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView priceView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            priceView = itemView.findViewById(R.id.item_price);
            itemView.setOnClickListener(new SelectSender());
            itemView.findViewById(R.id.remove_item).setOnClickListener(new Deleter());
            itemView.findViewById(R.id.edit_item).setOnClickListener(new Editer());
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
                listener.onDelete(data.get(selectedPosition).getId());
                data.remove(selectedPosition);
                notifyItemRemoved(selectedPosition);
            }
        }

        public class Editer implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                selectedPosition = getLayoutPosition();
                Log.i(TAG, "edit: " + data.get(selectedPosition));
                listener.onEdit(data.get(selectedPosition).getId(), selectedPosition);
                //data.remove(selectedPosition);
                //notifyItemRemoved(selectedPosition);
            }
        }
    }

}