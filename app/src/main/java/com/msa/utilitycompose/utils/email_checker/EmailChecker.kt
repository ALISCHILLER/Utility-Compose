package com.msa.utilitycompose.utils.email_checker

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import java.util.regex.Pattern

/**
 * کلاس حاوی توابع کمکی برای بررسی صحت آدرس ایمیل
 */
object EmailChecker {

    /**
     * بررسی صحت آدرس ایمیل
     *
     * @param email آدرس ایمیل وارد شده
     * @return true اگر آدرس معتبر باشد، وگرنه false
     */
    fun isValidEmail(email: String): Boolean {
        // تعریف الگوی معتبر برای آدرس ایمیل
        val emailPattern = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        )

        // بررسی تطابق ایمیل وارد شده با الگو
        return emailPattern.matcher(email).matches()
    }
}

/**
 * صفحه Compose برای تست صحت آدرس ایمیل
 */
@Composable
fun EmailCheckerScreen() {
    // حالت ذخیره سازی ایمیل وارد شده و نتیجه اعتبارسنجی
    val email = remember { mutableStateOf(TextFieldValue()) }
    val isValid = remember { mutableStateOf(false) }

    // فضای صفحه
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // فیلد متنی برای وارد کردن ایمیل
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                // اعتبارسنجی ایمیل هنگام تغییر مقدار
                isValid.value = EmailChecker.isValidEmail(it.text)
            },
            label = { Text("آدرس ایمیل") },
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
            Text("بررسی ایمیل")
        }

        // نمایش نتیجه اعتبارسنجی
        if (email.value.text.isNotEmpty()) {
            Text(
                text = if (isValid.value) "ایمیل معتبر است" else "ایمیل نامعتبر است",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

/**
 * پیش‌نمایش برای صفحه بررسی ایمیل
 */
@Preview(showBackground = true)
@Composable
fun PreviewEmailCheckerScreen() {
    EmailCheckerScreen()
}
