package com.msa.utilitycompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// رنگ‌های تم تاریک
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// رنگ‌های تم روشن
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun UtilityComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // به طور پیش‌فرض از تم سیستم استفاده می‌شود
    dynamicColor: Boolean = true, // برای رنگ‌های داینامیک اندروید ۱۲ به بالا
    content: @Composable () -> Unit // محتوای اصلی که باید در تم نمایش داده شود
) {
    // دریافت وضعیت تم پویا برای اندروید 12 و بالاتر
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // اگر تم پویا فعال باشد، رنگ‌ها از سیستم دریافت می‌شوند
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme // اگر تم تاریک فعال باشد
        else -> LightColorScheme // در غیر این صورت تم روشن
    }

    // اعمال تم با استفاده از MaterialTheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // تایپوگرافی پیش‌فرض
        content = content
    )
}
