package com.msa.utilitycompose.utils.conversionUtils



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlin.math.pow
import kotlin.math.roundToInt

object ConversionUtils {

    // تبدیل DP به PX
    fun dpToPx(dp: Float, density: Float): Float = dp * density

    // تبدیل PX به DP
    fun pxToDp(px: Float, density: Float): Float = px / density

    // گرد کردن یک عدد به تعداد مشخصی از اعشار
    fun round(value: Double, decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return (value * factor).roundToInt() / factor
    }

    // تبدیل سانتی‌گراد به فارنهایت
    fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9 / 5) + 32

    // تبدیل فارنهایت به سانتی‌گراد
    fun fahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5 / 9

    // قالب‌بندی عدد با جداکننده هزارگان (اضافه کردن ویرگول)
    fun formatWithCommas(number: Long): String = "%,d".format(number)

    // تبدیل عدد به درصد با اعشار مشخص
    fun toPercentage(value: Double, decimals: Int): String {
        val roundedValue = round(value * 100, decimals)
        return "$roundedValue%"
    }

    // محاسبه نسبت بین دو عدد و تبدیل به درصد
    fun calculateRatioPercentage(part: Double, total: Double, decimals: Int): String {
        return if (total != 0.0) toPercentage(part / total, decimals) else "0.0%"
    }

    // قالب‌بندی عدد با نمایش تعداد اعشار مشخص
    fun formatWithDecimals(number: Double, decimals: Int): String {
        return String.format("%.${decimals}f", number)
    }

}

// کامپوزیت برای تست و پیش‌نمایش توابع ابزارهای تبدیل
@Composable
fun ConversionUtilsPreview() {
    val density = LocalDensity.current.density

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // مثال‌هایی از استفاده ابزارهای تبدیل
        Text("10 dp به px: ${ConversionUtils.dpToPx(10f, density)} px")
        Text("50 px به dp: ${ConversionUtils.pxToDp(50f, density)} dp")
        Text("گرد کردن 123.456789 به 2 اعشار: ${ConversionUtils.round(123.456789, 2)}")
        Text("25°C به فارنهایت: ${ConversionUtils.celsiusToFahrenheit(25.0)}°F")
        Text("77°F به سانتی‌گراد: ${ConversionUtils.fahrenheitToCelsius(77.0)}°C")
        Text("عدد قالب‌بندی‌شده: ${ConversionUtils.formatWithCommas(1000000L)}")
        Text("تبدیل 0.875 به درصد: ${ConversionUtils.toPercentage(0.875, 2)}")
        Text("نسبت 25 به 100: ${ConversionUtils.calculateRatioPercentage(25.0, 100.0, 2)}")
        Text("قالب‌بندی عدد با 3 اعشار: ${ConversionUtils.formatWithDecimals(123.456789, 3)}")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConversionUtils() {
    MaterialTheme {
        ConversionUtilsPreview()
    }
}
