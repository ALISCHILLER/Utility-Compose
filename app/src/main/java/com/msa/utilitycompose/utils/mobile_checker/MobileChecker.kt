package com.msa.utilitycompose.utils.mobile_checker

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import java.util.regex.Pattern


object MobileChecker {

    /**
     * بررسی صحت شماره موبایل ایران
     *
     * @param mobile شماره موبایل وارد شده
     * @return true اگر شماره معتبر باشد، وگرنه false
     */
    fun isValidMobile(mobile: String): Boolean {
        // تعریف الگوی معتبر برای شماره موبایل ایران با پیش‌شماره +98 یا 09
        val mobilePattern = Pattern.compile("^((\\+98|0)9[0-9]{9})$")

        // بررسی تطابق شماره وارد شده با الگو
        return mobilePattern.matcher(mobile).matches()
    }
}






/**
 * صفحه Compose برای تست صحت شماره موبایل ایران
 */
@Composable
fun MobileCheckerScreen() {
    // حالت ذخیره سازی شماره موبایل وارد شده و نتیجه اعتبارسنجی
    val mobile = remember { mutableStateOf(TextFieldValue()) }
    val isValid = remember { mutableStateOf(false) }

    // فضای صفحه
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // فیلد متنی برای وارد کردن شماره موبایل
        OutlinedTextField(
            value = mobile.value,
            onValueChange = {
                mobile.value = it
                // اعتبارسنجی شماره موبایل هنگام تغییر مقدار
                isValid.value = MobileChecker.isValidMobile(it.text)
            },
            label = { Text("شماره موبایل") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // دکمه برای نمایش نتیجه
        Button(
            onClick = {
                if (isValid.value) {

                } else {

                }
            }
        ) {
            Text("بررسی شماره موبایل")
        }

        // نمایش نتیجه اعتبارسنجی
        if (mobile.value.text.isNotEmpty()) {
            Text(
                text = if (isValid.value) "شماره موبایل معتبر است" else "شماره موبایل نامعتبر است",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

/**
 * پیش‌نمایش برای صفحه بررسی شماره موبایل
 */
@Preview(showBackground = true)
@Composable
fun PreviewMobileCheckerScreen() {
    MobileCheckerScreen()
}
