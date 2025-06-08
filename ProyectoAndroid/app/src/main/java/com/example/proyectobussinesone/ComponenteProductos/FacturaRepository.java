package com.example.proyectobussinesone.ComponenteProductos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proyectobussinesone.ApiService;
import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto;
import com.example.proyectobussinesone.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacturaRepository {
    public interface FacturaCallback {
        void onSuccess();
        void onError(String errorMsg);
    }

    /**
     * Envía el objeto FacturaSimplePostRequestDto al endpoint REST.
     */
    public void crearFacturaSimple(@NonNull FacturaPostRequestDto dto,@NonNull FacturaCallback callback) {
        ApiService api = RetrofitClient.INSTANCE.getModuloApiService();
        Call<FacturaPostRequestDto> call = api.crearFacturaSimple(dto);

        Log.d(TAG, "crearFactura: enviando DTO -> " + dto.toString());
        call.enqueue(new Callback<FacturaPostRequestDto>() {
            @Override
            public void onResponse(@NonNull Call<FacturaPostRequestDto> call, @NonNull Response<FacturaPostRequestDto> response) {

                Log.d(TAG, "onResponse: HTTP " + response.code());
                if (response.isSuccessful()) {
                   FacturaPostRequestDto facturaGuardada = response.body();
                    Log.d("FacturaPOST", "Factura creada: ID = "
                            + ", Fecha = " + facturaGuardada.getFecha()
                            + ", Total = " + facturaGuardada.getPrecioTotal());
                    facturaGuardada.getItems().forEach( a -> {
                        Log.d(TAG, "onResponse: " + a.toString());
                    });
                    Log.d(TAG, "onResponse: Éxito (isSuccessful), body=null o vacío");
                    callback.onSuccess();
                } else {
                    String msg = "Error servidor: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            String cuerpoError = response.errorBody().string();
                            msg += " - " + cuerpoError;
                            Log.e(TAG, "onResponse: errorBody -> " + cuerpoError);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: no se pudo leer errorBody", e);
                        }
                    }
                    Log.e(TAG, "onResponse: falla servidor -> " + msg);
                    callback.onError(msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacturaPostRequestDto> call, @NonNull Throwable t) {
                String msg = "Fallo de red: " + t.getLocalizedMessage();
                Log.e(TAG, "onFailure: ", t);
                callback.onError("Fallo de red: " + t.getMessage());
            }
        });
    }
}
