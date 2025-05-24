package com.example.proyectobussinesone.ComponenteProductos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.example.proyectobussinesone.databinding.FragmentCrearProductoBinding;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CrearProductoFragment extends Fragment {

    private FragmentCrearProductoBinding binding;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private ImageCapture imageCapture;
    private File photoFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCrearProductoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Request camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }

        binding.btnCapture.setOnClickListener(v -> takePhoto());
        binding.btnSave.setOnClickListener(v -> saveData());
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(requireActivity().getWindowManager().getDefaultDisplay().getRotation())
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("PhotoCapture", "Error initializing camera", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }


    private Bitmap rotateBitmapIfRequired(String photoPath) throws IOException {
        ExifInterface exif = new ExifInterface(photoPath);
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return BitmapFactory.decodeFile(photoPath);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        return Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void takePhoto() {
        if (imageCapture == null) return;
        // Create file in cache
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(System.currentTimeMillis());
        photoFile = new File(requireContext().getCacheDir(), "IMG_" + timestamp + ".jpg");

        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();
        imageCapture.takePicture(options,
                ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        try {
                            Bitmap corrected = rotateBitmapIfRequired(photoFile.getAbsolutePath());
                            binding.capturedImage.setImageBitmap(corrected);

                            Bitmap barcodeBmp = generateBarcode(timestamp);
                            binding.barcodeImage.setImageBitmap(barcodeBmp);
                            int paddingPx = (int)(4 * getResources().getDisplayMetrics().density);

                            // aplica fondo blanco y padding
                            binding.barcodeImage.setBackgroundColor(Color.WHITE);
                            binding.barcodeImage.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
                        } catch (IOException e) {
                            Log.e("PhotoCapture", "Exif rotation failed", e);
                            // en última instancia, muestra el original:
                            binding.capturedImage.setImageBitmap(
                                    BitmapFactory.decodeFile(photoFile.getAbsolutePath()));
                        }
                        Toast.makeText(requireContext(), "Foto capturada y codigo generado", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("PhotoCapture", "Error taking photo", exception);
                        Toast.makeText(requireContext(), "Error capturando foto", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private Bitmap generateBarcode(String content) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.CODE_128, 600, 200);
            Bitmap bmp = Bitmap.createBitmap(matrix.getWidth(), matrix.getHeight(),
                    Bitmap.Config.RGB_565);
            for (int x = 0; x < matrix.getWidth(); x++) {
                for (int y = 0; y < matrix.getHeight(); y++) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            Log.e("CrearProducto", "Error generando barcode", e);
            return Bitmap.createBitmap(1,1, Bitmap.Config.ALPHA_8);
        }
    }
    private void saveData() {
        String name = binding.editName.getText().toString().trim();
        String price = binding.editPrice.getText().toString().trim();
        if (photoFile == null) {
            Toast.makeText(requireContext(), "Toma una foto primero", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(requireContext(), "Rellena nombre y precio", Toast.LENGTH_SHORT).show();
            return;
        }
        // Aquí guardas la ruta de photoFile y los datos
        Toast.makeText(requireContext(), "Datos guardados: " + name + " - €" + price, Toast.LENGTH_LONG).show();

        // ✅ Limpiar información mostrada
        binding.capturedImage.setImageDrawable(null);
        binding.barcodeImage.setImageDrawable(null);
        binding.editName.setText("");
        binding.editPrice.setText("");
        photoFile = null; // Reinicia el estado de la foto también

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
        }
    }
}
