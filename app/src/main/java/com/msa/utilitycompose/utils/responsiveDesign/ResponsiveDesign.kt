package com.msa.utilitycompose.utils.responsiveDesign

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

/**
 * Utility برای مدیریت اندازه المان‌ها بر اساس سایز صفحه
 * این تابع بر اساس عرض صفحه سایزها را تغییر می‌دهد.
 */
@Composable
fun responsiveWidth(baseWidth: Dp, smallScreenFactor: Float = 0.8f, largeScreenFactor: Float = 1.2f): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return when {
        screenWidth < 600.dp -> baseWidth * smallScreenFactor
        screenWidth > 840.dp -> baseWidth * largeScreenFactor
        else -> baseWidth
    }
}

/**
 * Utility برای مدیریت سایز فونت بر اساس سایز صفحه
 */
@Composable
fun responsiveFontSize(baseSize: Int, smallScreenFactor: Float = 0.8f, largeScreenFactor: Float = 1.2f): Float {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return when {
        screenWidth < 600.dp -> baseSize * smallScreenFactor
        screenWidth > 840.dp -> baseSize * largeScreenFactor
        else -> baseSize.toFloat()
    }
}

/**
 * Composable برای نمایش محتوای رسپانسیو
 * شامل متن و دکمه که بر اساس سایز صفحه تغییر می‌کنند
 */
@Composable
fun ResponsiveDesignDemo() {
    val buttonWidth = responsiveWidth(200.dp)
    val fontSize = responsiveFontSize(16)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "طراحی رسپانسیو در Jetpack Compose",
            fontSize = fontSize.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* اکشن کلیک */ },
            modifier = Modifier.width(buttonWidth)
        ) {
            Text("دکمه رسپانسیو")
        }
    }
}

/**
 * پیش‌نمایش برای مشاهده طراحی رسپانسیو
 */
@Preview(showBackground = true, widthDp = 480, heightDp = 800)
@Composable
fun PreviewResponsiveDesignSmallScreen() {
    ResponsiveDesignDemo()
}

@Preview(showBackground = true, widthDp = 1080, heightDp = 1920)
@Composable
fun PreviewResponsiveDesignLargeScreen() {
    ResponsiveDesignDemo()
}
