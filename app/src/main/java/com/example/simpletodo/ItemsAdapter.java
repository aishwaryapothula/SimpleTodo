package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adaptor - for displaying data from the model into a row in the recycler view,
// Give viewholder belonging to ItemsAdaptor class as parameter to Adaptor
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    // To tell adapter of what is happening in Main activity.
    // Main activity needs position to tell adapter
    public interface OnLongClickListener extends View.OnLongClickListener {
        void onItemLongClicked(int position);

        boolean onLongClick(View v, int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    // Constructor
    public ItemsAdapter(List<String> items, View.OnLongClickListener longClickListener){
        this.items = items;
        this.longClickListener = (OnLongClickListener) longClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }

    @Override
    // For taking data at a particular position and putting it in a view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Grab the item at a particular position
       String item =  items.get(position);

       // BInd the item into the specified view holder
        holder.bind(item);

    }
    // Tells the recycler view how many items are on the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        // Creating constructor, similar to self
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // To update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // To remove an item from the recycler view by long clicking on it
                    // Notifying the listener position that was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
