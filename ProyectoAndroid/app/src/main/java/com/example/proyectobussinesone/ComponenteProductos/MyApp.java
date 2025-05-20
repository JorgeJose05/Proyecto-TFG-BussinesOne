package com.example.proyectobussinesone.ComponenteProductos;

import android.app.Application;
import android.util.Log;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;

public class MyApp extends Application implements CameraXConfig.Provider {
    @Override
    public CameraXConfig getCameraXConfig() {
        // Usa la configuración por defecto de Camera2 y log sólo errores
        return CameraXConfig.Builder
                .fromConfig(Camera2Config.defaultConfig())
                .setMinimumLoggingLevel(Log.ERROR)
                .build();
    }
}
