package com.example.proyectobussinesone.ComponenteProductos.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectobussinesone.ApiService;
import com.example.proyectobussinesone.RetrofitClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kotlinx.coroutines.CoroutineScope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectobussinesone.ApiService;
import com.example.proyectobussinesone.RetrofitClient;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Response;
public class ProductoViewModel extends ViewModel {

    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final ProductoRepository repository = new ProductoRepository();

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void crearProducto(ProductoPostRequestDto dto) {
        repository.crearProducto(dto, new ProductoRepository.ProductoCallback() {
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
