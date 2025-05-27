
package com.example.proyectobussinesone.ComponenteProductos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectobussinesone.ComponenteProductos.models.Product;
import com.example.proyectobussinesone.R;

import java.util.Locale;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ViewHolder> {
    public ProductAdapter() {
        super(new DiffUtil.ItemCallback<Product>() {
            @Override public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) { return oldItem.code.equals(newItem.code); }
            @Override public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) { return oldItem.equals(newItem); }
        });
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = getItem(position);
        holder.tvName.setText(p.name);
        holder.tvPrice.setText(String.format(Locale.getDefault(), "€%.2f", p.price));
        holder.tvCode.setText(p.code);
        holder.tvQuantity.setText("Cantidad: " + p.quantity);

        holder.btnIncrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            quantity++;
            holder.tvQuantity.setText(String.valueOf(quantity));
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            if (quantity > 0) quantity--;
            holder.tvQuantity.setText(String.valueOf(quantity));
        });


        if (p.imagePath != null && !p.imagePath.isEmpty()) {
            Bitmap bmp = null;

            // Comprobamos si es Base64
            if (p.imagePath.length() > 100) {
                try {
                    byte[] decodedBytes = Base64.decode(p.imagePath, Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                } catch (Exception e) {
                    e.printStackTrace();  // Verás si hay errores al decodificar
                }
            }

            if (bmp != null) {
                holder.imgProduct.setImageBitmap(bmp);
            } else {
                setPlaceholder(holder); // si falla, pone imagen por defecto
            }
        } else {
            setPlaceholder(holder);
        }

        // Botón +
        holder.btnIncrease.setOnClickListener(v -> {
            p.setQuantity(p.getQuantity() + 1);
            notifyItemChanged(position);
        });

        // Botón -
        holder.btnDecrease.setOnClickListener(v -> {
            if (p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);
                notifyItemChanged(position);
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvCode, tvQuantity;
        Button btnIncrease, btnDecrease;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName     = itemView.findViewById(R.id.tvName);
            tvPrice    = itemView.findViewById(R.id.tvPrice);
            tvCode     = itemView.findViewById(R.id.tvCode);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
    private void setPlaceholder(ViewHolder holder) {
        int resId = holder.itemView.getContext().getResources().getIdentifier(
                "ic_placeholder", "drawable", holder.itemView.getContext().getPackageName());
        if (resId != 0) {
            holder.imgProduct.setImageResource(resId);
        } else {
            holder.imgProduct.setImageDrawable(null);
        }
    }

}