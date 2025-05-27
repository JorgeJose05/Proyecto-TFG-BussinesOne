
package com.example.proyectobussinesone.ComponenteProductos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectobussinesone.ComponenteProductos.models.Product;
import com.example.proyectobussinesone.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductListFragment extends Fragment {
    private SharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_barcode_list, container, false);
        RecyclerView rv = root.findViewById(R.id.recyclerView);
        Button btnBack = root.findViewById(R.id.ReturnToScan);
        Button CobrarFactura = root.findViewById(R.id.GoToBiller);
        TextView textDate = root.findViewById(R.id.textDate);
        TextView textTotalPrice = root.findViewById(R.id.textTotalPrice);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ProductAdapter adapter = new ProductAdapter();
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(adapter);
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.submitList(products);
            updateTotalPrice(products, textTotalPrice);
        });

        // Set current date
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date());
        textDate.setText("Fecha: " + currentDate);

        // Back button action
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        CobrarFactura.setOnClickListener(v -> {
            List<Product> products = viewModel.getProducts().getValue();
            if (products == null || products.isEmpty()) {
                return;
            }

            double total = 0;
            for (Product p : products) {
                total += p.price * p.quantity;
            }

            String message = "Total a pagar: €" + String.format(Locale.getDefault(), "%.2f", total);

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar compra")
                    .setMessage(message)
                    .setPositiveButton("Confirmar", (dialog, which) -> {
                        // Aquí puedes vaciar el carrito o navegar a otra pantalla
                        viewModel.clearProducts();  // Asegúrate de que este método existe

                        // Reemplaza con el fragmento del menú
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.childFragmentContainer, new Menu()) // Asegúrate del ID correcto del contenedor
                                .commit();

                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        return root;
    }


    private void updateTotalPrice(List<Product> products, TextView textTotalPrice) {
        double total = 0;
        for (Product p : products) {
            total += p.price * p.quantity;  // Asegúrate de tener el campo quantity en Product
        }
        textTotalPrice.setText("Total: €" + String.format(Locale.getDefault(), "%.2f", total));
    }
}