package com.msa.utilitycompose.utils.snackbarToastUtils

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Utility برای نمایش Toast به کاربر
 * این تابع یک پیام کوتاه را در پایین صفحه نمایش می‌دهد.
 */
@Composable
fun showToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Utility برای نمایش Snackbar
 * این تابع با استفاده از [SnackbarHostState] پیام را به صورت اسنک‌بار نمایش می‌دهد.
 *
 * @param snackbarHostState کنترل وضعیت نمایش Snackbar
 * @param message پیام مورد نظر برای نمایش
 */
suspend fun showSnackbar(snackbarHostState: SnackbarHostState, message: String): SnackbarResult {
    return snackbarHostState.showSnackbar(message)
}

/**
 * Composable برای پیش‌نمایش Toast و Snackbar
 * شامل دو دکمه برای تست کردن قابلیت نمایش پیام‌ها
 */
@Composable
fun SnackbarAndToastDemo() {
    // برای مدیریت حالت Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // دکمه برای نمایش Toast
            Button(onClick = {
                // فراخوانی Utility Toast
                Toast.makeText(context, "این یک پیام Toast است!", Toast.LENGTH_SHORT).show()
            }) {
                Text("نمایش Toast")
            }

            // فاصله بین دکمه‌ها
            Spacer(modifier = Modifier.padding(8.dp))

            // دکمه برای نمایش Snackbar
            Button(onClick = {
                coroutineScope.launch {
                    // فراخوانی Utility Snackbar
                    showSnackbar(snackbarHostState, "این یک پیام Snackbar است!")
                }
            }) {
                Text("نمایش Snackbar")
            }
        }
    }
}

/**
 * پیش‌نمایش برای نمایش عملکرد Toast و Snackbar
 * این تابع فقط برای تست و مشاهده در محیط Android Studio استفاده می‌شود.
 */
@Preview(showBackground = true)
@Composable
fun PreviewSnackbarAndToastDemo() {
    SnackbarAndToastDemo()
}
