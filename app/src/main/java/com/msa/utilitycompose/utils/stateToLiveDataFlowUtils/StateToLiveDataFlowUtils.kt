package com.msa.utilitycompose.utils.stateToLiveDataFlowUtils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msa.utilitycompose.utils.stateToLiveDataFlowUtils.StateToLiveDataFlowUtils.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Utility class for converting MutableState to LiveData or Flow
 */
object StateToLiveDataFlowUtils {

    /**
     * تبدیل MutableState به LiveData.
     * این تابع به شما این امکان را می‌دهد که مقدار داخل MutableState را به LiveData تبدیل کنید.
     * LiveData برای استفاده در ViewModel ها یا برای اشتراک‌گذاری داده‌ها با سایر قسمت‌های برنامه مناسب است.
     */
    fun <T> MutableState<T>.asLiveData(): LiveData<T> {
        val liveData = MutableLiveData<T>()
        liveData.value = this.value
        return liveData
    }

    /**
     * تبدیل MutableState به Flow.
     * این تابع یک جریان از داده‌ها را تولید می‌کند که به شما امکان می‌دهد تا تغییرات را به‌صورت جریان داده‌ها دنبال کنید.
     * Flow برای استفاده در حالت‌های که نیاز به پردازش داده‌ها یا نظارت غیرهمزمان داریم، مفید است.
     */
    fun <T> MutableState<T>.asFlow(): Flow<T> = flow {
        emit(this@asFlow.value) // مقدار اولیه را ارسال می‌کنیم
        // وقتی که مقدار تغییر کند، آن را ارسال می‌کنیم
        // برای این منظور از یک بی‌نهایت تکرار استفاده می‌کنیم.
        while (true) {
            kotlinx.coroutines.delay(50) // تاخیر 50 میلی‌ثانیه برای چک کردن تغییرات
            emit(this@asFlow.value) // ارسال مقدار تغییر یافته
        }
    }
}


@Preview
@Composable
fun MainScreen() {
    // ایجاد یک MutableState برای ذخیره مقدار شمارنده
    val counterState: MutableState<Int> = remember { mutableStateOf(0) }

    // تبدیل MutableState به Flow و استفاده از collectAsState برای جمع‌آوری مقادیر آن
    val counterFlow = counterState.asFlow()

    // استفاده از collectAsState برای مشاهده تغییرات در Flow
    val counterValue by counterFlow.collectAsState(initial = 0)

    // UI برای نمایش وضعیت و دکمه برای تغییر آن
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Current count: $counterValue")

        Button(
            onClick = {
                counterState.value += 1 // افزایش مقدار شمارنده
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Increase Counter")
        }

        // نمایش مقدار شمارنده از Flow
        Text(text = "Counter from Flow: $counterValue")
    }
}
