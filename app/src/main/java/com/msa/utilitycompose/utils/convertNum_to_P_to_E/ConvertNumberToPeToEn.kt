package com.msa.utilitycompose.utils.convertNum_to_P_to_E

import androidx.compose.ui.tooling.preview.Preview



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * توابع تبدیل اعداد فارسی به انگلیسی و انگلیسی به فارسی
 */
object ConvertNumberUtils {
    private val persianDigits = arrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
    private val englishDigits = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    fun convertPersianToEnglish(input: String): String {
        val builder = StringBuilder()
        input.forEach { char ->
            val index = persianDigits.indexOf(char)
            builder.append(if (index != -1) englishDigits[index] else char)
        }
        return builder.toString()
    }

    fun convertEnglishToPersian(input: String): String {
        val builder = StringBuilder()
        input.forEach { char ->
            val index = englishDigits.indexOf(char)
            builder.append(if (index != -1) persianDigits[index] else char)
        }
        return builder.toString()
    }
}

@Composable
fun NumberConversionScreen() {
    // متغیرهای حالت برای ذخیره ورودی و خروجی
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var convertedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "تبدیل اعداد فارسی و انگلیسی",
            style = MaterialTheme.typography.headlineSmall
        )

        // فیلد ورودی برای وارد کردن متن
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("متن ورودی") },
            placeholder = { Text("متن خود را وارد کنید") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Hide Keyboard */ }),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // دکمه تبدیل از فارسی به انگلیسی
            Button(
                onClick = { convertedText = ConvertNumberUtils.convertPersianToEnglish(inputText.text) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "تبدیل به انگلیسی")
            }

            // دکمه تبدیل از انگلیسی به فارسی
            Button(
                onClick = { convertedText = ConvertNumberUtils.convertEnglishToPersian(inputText.text) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "تبدیل به فارسی")
            }
        }

        // نمایش خروجی تبدیل شده
        if (convertedText.isNotEmpty()) {
            Text(
                text = "خروجی: $convertedText",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNumberConversionScreen() {
    MaterialTheme {
        NumberConversionScreen()
    }
}
