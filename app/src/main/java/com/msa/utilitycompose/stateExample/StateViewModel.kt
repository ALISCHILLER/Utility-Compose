package com.msa.utilitycompose.stateExample

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * کلاس ViewModel برای مدیریت وضعیت (State Management)
 *
 * این کلاس مسئول نگهداری، مدیریت و بروزرسانی وضعیت‌های مختلف است که توسط UI (Jetpack Compose) استفاده می‌شود.
 * از ابزارهای مختلف مانند StateFlow، LiveData و SharedFlow برای پاسخگویی به نیازهای مدیریت وضعیت استفاده شده است.
 */
class StateViewModel : ViewModel() {

    // StateFlow: یک ابزار واکنش‌پذیر (Reactive) برای ذخیره و انتشار وضعیت قابل مشاهده است.
    // این نوع داده برای مدیریت وضعیت‌هایی که UI باید به صورت مداوم به‌روزرسانی شوند استفاده می‌شود.

    // MutableStateFlow: نسخه‌ی قابل تغییر StateFlow
    // مقدار اولیه صفر برای شمارنده (counter)
    private val _counter = MutableStateFlow(0)

    // StateFlow: مقدار غیرقابل تغییر (Read-Only) از MutableStateFlow
    // این حالت توسط UI مشاهده می‌شود.
    val counter: StateFlow<Int> = _counter

    // MutableStateFlow برای نگهداری متن وارد شده توسط کاربر
    private val _text = MutableStateFlow("")

    // StateFlow فقط خواندنی برای نمایش مقدار متن در UI
    val text: StateFlow<String> = _text

    // LiveData: ابزار سنتی و قدیمی‌تر برای نگهداری داده‌های واکنش‌پذیر
    // مقدار اولیه یک رشته خالی ("")
    private val _liveDataText = MutableLiveData("")

    // LiveData فقط خواندنی برای مشاهده در UI
    val liveDataText: LiveData<String> = _liveDataText

    // SharedFlow: ابزار جدید برای ارسال رویدادها (Events) به صورت یک طرفه (One-Way)
    // این نوع داده به خصوص برای مدیریت رویدادهایی که نباید تکرار شوند مفید است (مانند نمایش پیام خطا)
    private val _events = MutableSharedFlow<String>()

    // SharedFlow فقط خواندنی که توسط UI مشاهده می‌شود.
    val events: SharedFlow<String> = _events

    /**
     * متد برای افزایش مقدار شمارنده
     * مقدار شمارنده (counter) به مقدار یک واحد افزایش پیدا می‌کند.
     */
    fun incrementCounter() {
        _counter.value++ // مقدار شمارنده را افزایش می‌دهد
    }

    /**
     * متد برای بروزرسانی متن
     * متن جدید را در StateFlow و LiveData ذخیره می‌کند.
     *
     * @param newText متن جدیدی که باید ذخیره شود.
     */
    fun updateText(newText: String) {
        _text.value = newText // مقدار متن در StateFlow بروزرسانی می‌شود
        _liveDataText.value = newText // مقدار متن در LiveData بروزرسانی می‌شود
    }

    /**
     * متد برای ارسال رویداد جدید
     * این متد یک پیام جدید را از طریق SharedFlow منتشر می‌کند.
     *
     * @param eventMessage پیام رویدادی که باید منتشر شود.
     */
    suspend fun sendEvent(eventMessage: String) {
        _events.emit(eventMessage) // پیام جدید در SharedFlow منتشر می‌شود
    }
}
