package ir.sban.intelligallery.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BlueLight,
    onPrimary = NeutralWhite,
    primaryContainer = BlueDark,
    onPrimaryContainer = BluePale,

    secondary = PurpleLight,
    onSecondary = NeutralWhite,
    secondaryContainer = PurpleDark,
    onSecondaryContainer = PurplePale,

    tertiary = TealBright,
    onTertiary = NeutralNearBlack,
    tertiaryContainer = TealDark,
    onTertiaryContainer = TealPale,

    background = NeutralNearBlack,
    onBackground = NeutralLightGray,

    surface = NeutralSurfaceDark,
    onSurface = NeutralLightGray,

    outline = NeutralMediumGray,

    error = ErrorLight,
    onError = NeutralWhite,
    errorContainer = ErrorDark,
    onErrorContainer = ErrorPale
)

// Light Theme Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = NeutralWhite,
    primaryContainer = BluePale,
    onPrimaryContainer = BlueDark,

    secondary = PurplePrimary,
    onSecondary = NeutralWhite,
    secondaryContainer = PurplePale,
    onSecondaryContainer = PurpleDark,

    tertiary = TealPrimary,
    onTertiary = NeutralNearBlack,
    tertiaryContainer = TealPale,
    onTertiaryContainer = TealDark,

    background = NeutralOffWhite,
    onBackground = NeutralNearBlack,

    surface = NeutralOffWhite,
    onSurface = NeutralNearBlack,

    outline = NeutralDarkGray,

    error = ErrorRed,
    onError = NeutralWhite,
    errorContainer = ErrorPale,
    onErrorContainer = ErrorDark
)

@Composable
fun IntelligalleryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}