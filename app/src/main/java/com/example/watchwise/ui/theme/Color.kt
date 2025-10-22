package com.example.watchwise.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary colors
val Primary = Color(0xFF2196F3)
val PrimaryDark = Color(0xFF1976D2)
val Secondary = Color(0xFF03DAC6)
val SecondaryDark = Color(0xFF018786)
val Background = Color(0xFF121212)
val Surface = Color(0xFF1E1E1E)
val Error = Color(0xFFCF6679)

// Dark theme
val DarkColorPalette = darkColorScheme(
    primary = Primary,
    onPrimary = Color.Black,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = Secondary,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFF03DAC6),
    onTertiary = Color.Black,
    background = Background,
    onBackground = Color.White,
    surface = Surface,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFCACACA),
    error = Error,
    onError = Color.Black
)

// Light theme
val LightColorPalette = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Secondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFB2DFDB),
    onSecondaryContainer = Color(0xFF002020),
    tertiary = Color(0xFF03DAC6),
    onTertiary = Color.Black,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF424242),
    error = Error,
    onError = Color.White
)