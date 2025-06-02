package com.example.proyectobussinesone.ComponenteProductos.models;

import android.util.Log;

import com.example.proyectobussinesone.ApiService;
import com.example.proyectobussinesone.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {

    public interface ProductoCallback {
        void onSuccess();
        void onError(String errorMsg);
    }

    public void crearProducto(ProductoPostRequestDto dto, ProductoCallback callback) {
        ApiService apiService = RetrofitClient.INSTANCE.getModuloApiService();

        Call<Void> call = apiService.crearProducto(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("ProductoRepository", "Producto creado con éxito");
                    callback.onSuccess();
                } else {
                    String msg = null;
                    try {
                        msg = "Error del servidor: " + response.errorBody().string();
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Sin cuerpo";

                        Log.e("ProductoRepository", "Error del servidor: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        Log.e("ProductoRepository", "Error leyendo el cuerpo del error", e);
                        throw new RuntimeException(e);
                    }
                    Log.e("ProductoRepository", msg);
                    callback.onError(msg);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String msg = "Error de red: " + t.getMessage();
                Log.e("ProductoRepository", msg);
                callback.onError(msg);
            }
        });
    }
}
