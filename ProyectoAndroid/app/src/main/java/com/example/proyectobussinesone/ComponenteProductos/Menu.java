package com.example.proyectobussinesone.ComponenteProductos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectobussinesone.R;


public class Menu extends Fragment {



    public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public Menu() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_comonente_prueba, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button btnCreate = view.findViewById(R.id.buttonCreate);
        Button btnScan   = view.findViewById(R.id.buttonScan);

        btnCreate.setOnClickListener(v -> {
            // Oculta los botones
            view.findViewById(R.id.panelButtons).setVisibility(View.GONE);
            // Muestra el fragment de creación
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.childFragmentContainer,
                            new CrearProductoFragment())
                    .commit();
        });

        btnScan.setOnClickListener(v -> {
            view.findViewById(R.id.panelButtons).setVisibility(View.GONE);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.childFragmentContainer,
                            new ComponentePrueba())
                    .commit();
        });
    }

}