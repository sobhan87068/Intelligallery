package ir.sban.intelligallery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.sban.intelligallery.R

// Load fonts (place font files in res/font/)
val Figtree = FontFamily(
    Font(R.font.figtree_light, FontWeight.Light),
    Font(R.font.figtree_regular, FontWeight.Normal),
    Font(R.font.figtree_medium, FontWeight.Medium),
    Font(R.font.figtree_semibold, FontWeight.SemiBold),
    Font(R.font.figtree_bold, FontWeight.Bold)
)

val BaiJamjuree = FontFamily(
    Font(R.font.baijamjuree_regular, FontWeight.Normal),
    Font(R.font.baijamjuree_medium, FontWeight.Medium),
    Font(R.font.baijamjuree_bold, FontWeight.Bold)
)

val Typography = Typography(
    /* Display Styles (Used for Logo/Big Headers) */
    displayLarge = TextStyle(
        fontFamily = BaiJamjuree,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = BaiJamjuree,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = BaiJamjuree,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),

    /* Headline Styles (Section Headers) */
    headlineLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),

    /* Title Styles (Card Titles) */
    titleLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    /* Body Styles (Main Content) */
    bodyLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    /* Label Styles (Buttons, Captions) */
    labelLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

/* Extension for Logo-Specific Text */
val Typography.logoText: TextStyle
    get() = TextStyle(
        fontFamily = BaiJamjuree,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    )