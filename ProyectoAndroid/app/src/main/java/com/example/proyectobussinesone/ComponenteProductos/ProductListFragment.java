
package com.example.proyectobussinesone.ComponenteProductos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectobussinesone.ComponenteProductos.models.FacturaItemDto;
import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto;
import com.example.proyectobussinesone.ComponenteProductos.models.Product;
import com.example.proyectobussinesone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        String currentDateIso = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
        textDate.setText("Fecha: " + currentDateIso);

        // Back button action
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        CobrarFactura.setOnClickListener(v -> {
            List<Product> products = viewModel.getProducts().getValue();
            if (products == null || products.isEmpty()) {
                Toast.makeText(requireContext(), "No hay productos en el carrito", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = 0;
            for (Product p : products) {
                total += p.price * p.quantity;
            }

            String message = "Total a pagar: €" + String.format(Locale.getDefault(), "%.2f", total);

            // Observamos si la creación salió bien o mal
            viewModel.getSuccess().observe(getViewLifecycleOwner(), wasSaved -> {
                if (Boolean.TRUE.equals(wasSaved)) {
                    Toast.makeText(requireContext(), "Factura guardada con éxito", Toast.LENGTH_SHORT).show();
                    viewModel.clearProducts();
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.childFragmentContainer, new Menu())
                            .commit();
                }
            });
            viewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
                if (errorMsg != null) {
                    Toast.makeText(requireContext(), "Error al guardar factura: " + errorMsg,
                            Toast.LENGTH_LONG).show();
                }
            });
            final double finaltotal = total;
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar compra")
                    .setMessage(message)
                    .setPositiveButton("Confirmar", (dialog, which) -> {


                        enviarFacturaSimple(currentDateIso, finaltotal, /* usuarioId = */ 1L, products);

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

    private void enviarFacturaSimple(
            @NonNull String fechaIso,
            double total,
            long usuarioId,
            @NonNull List<Product> products) {

        // 1) Convertimos cada Product en FacturaItemDto
        List<FacturaItemDto> itemsDto = new ArrayList<>();
        for (Product p : products) {
            Long prodId;
            try {
                // asumimos que p.code es un número válido (por ejemplo "42", "17", etc.)
                prodId = Long.parseLong(p.code);
            } catch (NumberFormatException ex) {
                Toast.makeText(requireContext(),
                        "El código de producto “" + p.code + "” no es numérico",
                        Toast.LENGTH_LONG).show();
                return;
            }
            FacturaItemDto item = new FacturaItemDto(
                    prodId,
                    p.quantity,
                    p.price
            );
            itemsDto.add(item);
        }

        // 2) Construimos el DTO completo
        FacturaPostRequestDto dto = new FacturaPostRequestDto(
                fechaIso,
                total,
                usuarioId,
                itemsDto
        );

        // 3) Observamos el LiveData de éxito/error en el ViewModel
        viewModel.getSuccess().observe(
                getViewLifecycleOwner(),
                wasSaved -> {
                    if (Boolean.TRUE.equals(wasSaved)) {
                        Toast.makeText(requireContext(),
                                "Factura guardada con éxito", Toast.LENGTH_SHORT).show();
                        viewModel.clearProducts();
                        // Volvemos al menú o a donde quieras
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.childFragmentContainer, new Menu())
                                .commit();
                    }
                }
        );
        viewModel.getError().observe(
                getViewLifecycleOwner(),
                errorMsg -> {
                    if (errorMsg != null) {
                        Toast.makeText(requireContext(),
                                "Error al guardar factura: " + errorMsg,
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        // 4) Por último, pedimos al ViewModel que haga el POST
        viewModel.crearFacturaSimple(dto);
    }

    private void updateTotalPrice(List<Product> products, TextView textTotalPrice) {
        double total = 0;
        for (Product p : products) {
            total += p.price * p.quantity;  // Asegúrate de tener el campo quantity en Product
        }
        textTotalPrice.setText("Total: €" + String.format(Locale.getDefault(), "%.2f", total));
    }
}