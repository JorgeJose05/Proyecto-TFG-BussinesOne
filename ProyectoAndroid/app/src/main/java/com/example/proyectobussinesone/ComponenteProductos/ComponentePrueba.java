package com.example.proyectobussinesone.ComponenteProductos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.proyectobussinesone.ApiService;
import com.example.proyectobussinesone.ComponenteProductos.models.Product;
import com.example.proyectobussinesone.R;
import com.example.proyectobussinesone.RetrofitClient;
import com.example.proyectobussinesone.databinding.FragmentComponentePruebaBinding;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComponentePrueba extends Fragment {

    private FragmentComponentePruebaBinding binding;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private BarcodeScanner scanner;
    private final List<String> detectedBarcodes = new ArrayList<>();
    private SharedViewModel viewModel;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = BarcodeScanning.getClient();
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComponentePruebaBinding.inflate(inflater, container, false);

        // Configurar botón para detectar el código almacenado
        binding.buttonDetectStored.setOnClickListener(v -> {
            // Obtén un Bitmap de la vista previa; puede ser null si aún no hay frame listo
            Bitmap previewBmp = binding.previewView.getBitmap();
            if (previewBmp == null) {
                Toast.makeText(requireContext(),
                        "Aún no hay imagen de cámara disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            // Procesa el Bitmap con ML Kit
            InputImage image = InputImage.fromBitmap(previewBmp, 0);
            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        if (!barcodes.isEmpty()) {
                            String rawValue = barcodes.get(0).getRawValue();
                            if (rawValue != null && !detectedBarcodes.contains(rawValue)) {
                                fetchAndAddProduct(rawValue);
                                /*
                                // 1) Obtén el objeto Product de tu catálogo
                                Product prod = getProductoByCode(rawValue);
                                if (prod != null) {
                                    // 2) Añádelo al ViewModel compartido
                                    viewModel.addProduct(prod);

                                    // 3) Genera y muestra el bitmap del código
                                    Bitmap barcodeBmp = generateBarcode(rawValue);
                                    binding.imageViewBarcode.setImageBitmap(barcodeBmp);

                                    // 4) Muestra la info del producto
                                    showProductInfo(rawValue);
                                } else {
                                    Toast.makeText(requireContext(),
                                            "Producto no encontrado para: " + rawValue,
                                            Toast.LENGTH_SHORT).show();
                                }
                                */
                            }else{
                                Toast.makeText(requireContext(),
                                        "Producto no encontrado para: " + rawValue,
                                        Toast.LENGTH_SHORT).show();
                                Log.e("ComponentePrueba", "Respuesta no exitosa: HTTP ");

                            }
                        } else {
                            Toast.makeText(requireContext(),
                                    "No se detectó ningún código de barras",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ComponentePrueba", "Error procesando imagen", e);
                        Toast.makeText(requireContext(),
                                "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
                    });

        });

        binding.btnConfirm.setOnClickListener(v -> {
            // navigate to list fragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.childFragmentContainer, new ProductListFragment())
                    .addToBackStack(null)     // para poder volver atrás
                    .commit();
        });

        return binding.getRoot();
    }

    private void fetchAndAddProduct(String rawValue) {
        ApiService api = RetrofitClient.INSTANCE.getModuloApiService();
        // Endpoint definido así: @GET("productos/GET/{id}") Call<Product> getProductoPorId(... )
        Call<Product> call = api.getProductoPorId(rawValue);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                // 1) Logueamos el código HTTP
                Log.d("ComponentePrueba", "onResponse: HTTP " + response.code());
                Log.d("ComponentePrueba", "Body: boyd " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Product prod = response.body();

                    // 4) Imprimimos en logcat los campos que vienen en prod
                    Log.d("ComponentePrueba", "Product recibido – código: " + prod.code
                            + ", name: " + prod.name
                            + ", price: " + prod.price
                            + ", imagePath: " + prod.imagePath
                            + ", quantity: " + prod.quantity
                            + ", creatorId: " + prod.creatorId);
                    // 2) Lo agregamos al ViewModel compartido
                    viewModel.addProduct(prod);

                    // 3) Mostramos el bitmap del código escaneado
                    Bitmap barcodeBmp = generateBarcode(rawValue);
                    binding.imageViewBarcode.setImageBitmap(barcodeBmp);

                    // 4) Mensaje de éxito con el nombre y precio
                    Toast.makeText(requireContext(),
                            prod.name + " — €" + prod.price,
                            Toast.LENGTH_SHORT).show();
                    detectedBarcodes.add(rawValue);
                } else {
                    // Si el servidor devolvió 404, 400, etc. o body == null
                    Toast.makeText(requireContext(),
                            "Producto no encontrado para: " + rawValue,
                            Toast.LENGTH_SHORT).show();
                    Log.e("ComponentePrueba", "Respuesta no exitosa: HTTP " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                // Error de red, servidor caído, etc.
                Toast.makeText(requireContext(),
                        "Error al conectar con el servidor: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e("ComponentePrueba", "onFailure Retrofit: ", t);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        PreviewView previewView = binding.previewView;
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                cameraProvider.bindToLifecycle(this,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview);

                //cameraProvider.unbindAll();
                //cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, analysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e("ComponentePrueba", "Error al iniciar cámara", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImageProxy(ImageProxy imageProxy) {
        if (imageProxy.getImage() == null) {
            imageProxy.close();
            return;
        }
        InputImage image = InputImage.fromMediaImage(
                imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());
        scanner.process(image)
                .addOnSuccessListener(this::handleBarcodes)
                .addOnFailureListener(e -> Log.e("ComponentePrueba", "Error escaneando", e))
                .addOnCompleteListener(task -> imageProxy.close());
    }

    private void handleBarcodes(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            String rawValue = barcode.getRawValue();
            if (rawValue != null && !detectedBarcodes.contains(rawValue)) {
                detectedBarcodes.add(rawValue);
                Bitmap barcodeBitmap = generateBarcode(rawValue);
                binding.imageViewBarcode.setImageBitmap(barcodeBitmap);
                Log.d("ComponentePrueba", "Leído: " + rawValue);
            }
        }
    }

    // Detecta el código almacenado en internal storage
    private void detectStoredBarcode() {
        try (FileInputStream fis = requireContext().openFileInput("barcode.png")) {
            Bitmap storedBmp = BitmapFactory.decodeStream(fis);
            InputImage image = InputImage.fromBitmap(storedBmp, 0);
            scanner.process(image)
                    .addOnSuccessListener(this::handleBarcodes)
                    .addOnFailureListener(e -> Log.e("ComponentePrueba", "Error detectando almacenado", e));
        } catch (IOException e) {
            Log.e("ComponentePrueba", "Error leyendo bitmap almacenado", e);
        }
    }


    private String readStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        return baos.toString("UTF-8");
    }

    private Bitmap generateBarcode(String content) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.CODE_128, 600, 300);
            Bitmap bmp = Bitmap.createBitmap(matrix.getWidth(), matrix.getHeight(), Bitmap.Config.RGB_565);
            for (int x = 0; x < matrix.getWidth(); x++) {
                for (int y = 0; y < matrix.getHeight(); y++) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            Log.e("ComponentePrueba", "Error generando código", e);
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        }
    }

    private void saveBitmapToInternal(Bitmap bmp, Context context, String fileName) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            Log.e("ComponentePrueba", "Error guardando bitmap", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
