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
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ComponentePrueba extends Fragment {

    private FragmentComponentePruebaBinding binding;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private BarcodeScanner scanner;
    private Map<String, Product> catalogMap;
    private final List<String> detectedBarcodes = new ArrayList<>();


    // Clase auxiliar para productos
    static class Product {
        String code;
        String name;
        double price;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = BarcodeScanning.getClient();
        catalogMap = loadCatalog(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComponentePruebaBinding.inflate(inflater, container, false);

        // Generar y mostrar un código de barras de ejemplo
        Bitmap bmp = generateBarcode("123456789012");
        binding.imageViewBarcode.setImageBitmap(null);
        saveBitmapToInternal(bmp, requireContext(), "barcode.png");

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
                            if (rawValue != null) {
                                // Genera y muestra el barcode
                                Bitmap barcodeBmp = generateBarcode(rawValue);
                                binding.imageViewBarcode.setImageBitmap(barcodeBmp);
                                // Guarda en la lista (permitiendo duplicados)
                                detectedBarcodes.add(rawValue);
                                showProductInfo(rawValue);
                            }
                        } else {
                            Toast.makeText(requireContext(),
                                    "No se detectó ningún código de barras", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ComponentePrueba", "Error procesando imagen", e);
                        Toast.makeText(requireContext(),
                                "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
                    });
        });


        return binding.getRoot();
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
                showProductInfo(rawValue);
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

    private void showProductInfo(String rawValue) {
        Product product = catalogMap.get(rawValue);
        if (product != null) {
            String msg = product.name + " — €" + product.price;
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Código no encontrado: " + rawValue,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Map<String, Product> loadCatalog(Context ctx) {
        try (InputStream is = ctx.getAssets().open("barcodes.json")) {
            String json = readStreamToString(is);
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> list = new Gson().fromJson(json, listType);
            Map<String, Product> map = new HashMap<>();
            for (Product p : list) map.put(p.code, p);
            return map;
        } catch (IOException e) {
            Log.e("ComponentePrueba", "Error cargando catálogo", e);
            return Collections.emptyMap();
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
