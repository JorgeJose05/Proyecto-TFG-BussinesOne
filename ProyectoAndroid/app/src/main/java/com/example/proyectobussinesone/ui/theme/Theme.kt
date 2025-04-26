package com.example.proyectobussinesone.ui.theme

import androidx.compose.ui.graphics.Color

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary        = AzulPrimario,
    secondary      = AzulSecundario,
    tertiary       = AzulTerciario,
    background     = FondoClaro,
    surface        = FondoClaro,
    onPrimary      = Color.White,
    onSecondary    = Color.White,
    onBackground   = Color.Black,
    onSurface      = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary        = AzulPrimario,
    secondary      = AzulSecundario,
    tertiary       = AzulTerciario,
    background     = FondoOscuro,
    surface        = FondoOscuro,
    onPrimary      = Color.White,
    onSecondary    = Color.White,
    onBackground   = Color.White,
    onSurface      = Color.White
)

@Composable
fun ProyectoBussinesOneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AppTypography,
        shapes      = AppShapes,
        content     = content
    )
}