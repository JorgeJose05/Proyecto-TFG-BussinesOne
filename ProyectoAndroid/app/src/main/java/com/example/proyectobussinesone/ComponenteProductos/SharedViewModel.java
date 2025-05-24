package com.example.proyectobussinesone.ComponenteProductos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<String>> barcodes = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getBarcodes() {
        return barcodes;
    }

    public void addBarcode(String code) {
        List<String> current = new ArrayList<>(barcodes.getValue());
        current.add(code);
        barcodes.setValue(current);
    }

    public void clearBarcodes() {
        barcodes.setValue(new ArrayList<>());
    }
}