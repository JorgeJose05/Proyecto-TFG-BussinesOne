package com.example.proyectobussinesone.ComponenteProductos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto;
import com.example.proyectobussinesone.ComponenteProductos.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> products =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FacturaRepository repository = new FacturaRepository();

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }

    public SharedViewModel() {
        // Mock: pre-poblamos con 4 productos de ejemplo
        List<Product> initial = new ArrayList<>();
        
        products.setValue(initial);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void addProduct(Product newProduct) {
        List<Product> currentList = products.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        boolean found = false;
        for (Product p : currentList) {
            if (p.code.equals(newProduct.code)) {
                p.quantity += 1; // Aumenta cantidad
                found = true;
                break;
            }
        }

        if (!found) {
            // Clona el producto para evitar modificar el catálogo original
            Product copy = new Product(newProduct);
            copy.quantity = 1;
            currentList.add(copy);
        }

        products.setValue(new ArrayList<>(currentList)); // Forzar notificación
    }

    public void clearProducts() {
        products.setValue(new ArrayList<>());
    }
    public void crearFacturaSimple(FacturaPostRequestDto dto) {
        repository.crearFacturaSimple(dto, new FacturaRepository.FacturaCallback() {
            @Override
            public void onSuccess() {
                success.postValue(true);
            }

            @Override
            public void onError(String errorMsg) {
                error.postValue(errorMsg);
            }
        });
    }
}