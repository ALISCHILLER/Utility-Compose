package com.msa.utilitycompose.utils.iraniNational_code_checker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

/**
 * کلاس حاوی توابع کمکی و عمومی که در بخش‌های مختلف برنامه استفاده می‌شوند
 */
object IraniNationalCodeChecker {

    /**
     * بررسی صحت کد ملی ایران
     *
     * @param nationalCode کد ملی وارد شده توسط کاربر
     * @return true اگر کد معتبر باشد، وگرنه false
     */
    fun isValidNationalCode(nationalCode: String): Boolean {
        // حذف فاصله‌ها و تبدیل به طول استاندارد 10 رقمی
        val code = nationalCode.trim().padStart(10, '0')

        // بررسی طول کد
        if (code.length != 10 || !code.all { it.isDigit() }) return false

        // استخراج رقم کنترل و ارقام دیگر
        val controlDigit = code.last().digitToInt()
        val digits = code.substring(0, 9).map { it.digitToInt() }

        // محاسبه رقم کنترل مورد انتظار
        val sum = digits.mapIndexed { index, digit -> digit * (10 - index) }.sum()
        val remainder = sum % 11
        val expectedControlDigit = if (remainder < 2) remainder else 11 - remainder

        return controlDigit == expectedControlDigit
    }
}

/**
 * صفحه Compose برای تست صحت کد ملی ایرانی
 */
@Composable
fun NationalCodeCheckerScreen() {
    // حالت ذخیره سازی کد ملی وارد شده و نتیجه اعتبارسنجی
    val nationalCode = remember { mutableStateOf(TextFieldValue()) }
    val nationalCodeText = remember { mutableStateOf("") }
    val isValid = remember { mutableStateOf(false) }
    val textValid = remember { mutableStateOf("") }
    // فضای صفحه
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // فیلد متنی برای وارد کردن کد ملی
        OutlinedTextField(
            value = nationalCode.value,
            onValueChange = {
                nationalCode.value = it
                nationalCodeText.value = it.text
                // اعتبارسنجی کد ملی هنگام تغییر مقدار
                isValid.value = IraniNationalCodeChecker.isValidNationalCode(it.text)
            },
            label = { Text("کد ملی") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // دکمه برای نمایش نتیجه
        Button(
            onClick = {
                if (isValid.value) {
                    textValid.value = "کد ملی معتبر است "
                } else {
                    textValid.value = "کد ملی معتبر نیست "
                }
            }
        ) {
            Text("بررسی کد ملی")
        }

        if (textValid.value.isNotEmpty())
            Text(
                text = "${nationalCodeText.value}   ${textValid.value}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

        // نمایش نتیجه اعتبارسنجی
        if (nationalCode.value.text.isNotEmpty()) {
            Text(
                text = if (isValid.value) "کد ملی معتبر است" else "کد ملی نامعتبر است",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

/**
 * پیش‌نمایش برای صفحه بررسی کد ملی
 */
@Preview(showBackground = true)
@Composable
fun PreviewNationalCodeCheckerScreen() {
    NationalCodeCheckerScreen()
}
