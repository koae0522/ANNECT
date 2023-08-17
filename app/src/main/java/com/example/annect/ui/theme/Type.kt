package com.example.annect.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.annect.R

// Set of Material typography styles to start with

val Mobo= FontFamily(
    Font(R.font.mobo_bold,FontWeight.Bold),
    Font(R.font.mobo_semibold,FontWeight.SemiBold),
    Font(R.font.mobo_regular,FontWeight.Normal),
    Font(R.font.mobo_extralight,FontWeight.ExtraLight)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Mobo,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Mobo,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)