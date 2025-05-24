
package com.example.proyectobussinesone.ComponenteProductos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectobussinesone.R;

import java.util.List;

public class BarcodeListFragment extends Fragment {
    private SharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_barcode_list, container, false);
        RecyclerView rv = root.findViewById(R.id.recyclerView);
        Button btnBack = root.findViewById(R.id.buttonBack);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        BarcodeAdapter adapter = new BarcodeAdapter();
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(adapter);

        viewModel.getBarcodes().observe(getViewLifecycleOwner(), adapter::submitList);

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        return root;
    }
}