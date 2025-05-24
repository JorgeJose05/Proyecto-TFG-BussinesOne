
package com.example.proyectobussinesone.ComponenteProductos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeAdapter extends ListAdapter<String, BarcodeAdapter.ViewHolder> {
    public BarcodeAdapter() {
        super(new DiffUtil.ItemCallback<String>() {
            @Override public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) { return oldItem.equals(newItem); }
            @Override public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) { return oldItem.equals(newItem); }
        });
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(android.R.id.text1);
        }
    }
}