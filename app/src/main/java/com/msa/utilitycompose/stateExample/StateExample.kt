package com.msa.utilitycompose.stateExample

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * کامپوزبل اصلی (Main Composable) برای نمایش انواع روش‌های مدیریت وضعیت در Jetpack Compose.
 *
 * این تابع با استفاده از انواع داده‌های واکنش‌پذیر مانند StateFlow، LiveData، SnapshotState و SharedFlow
 * نحوه کار با وضعیت‌ها را در Jetpack Compose به نمایش می‌گذارد.
 *
 * @param viewModel نمونه‌ای از ViewModel که وضعیت‌ها و داده‌های موردنیاز را مدیریت می‌کند.
 */

@Preview
@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(viewModel: StateViewModel = viewModel()) {
    // دریافت مقدار StateFlow از ViewModel
    val counterState by viewModel.counter.collectAsState()
    val textState by viewModel.text.collectAsState()

    // مشاهده مقدار LiveData به عنوان یک State در Compose
    val liveDataTextState by viewModel.liveDataText.observeAsState("")

    // DerivedStateOf: وضعیت مشتق شده از سایر وضعیت‌ها
    val derivedState = derivedStateOf { "Counter: $counterState, Text Length: ${textState.length}" }

    // SnapshotStateList: لیستی واکنش‌پذیر برای مدیریت داده‌های لیست
    val stateList = remember { mutableStateListOf("Item 1", "Item 2") }

    // SnapshotStateMap: نقشه‌ای واکنش‌پذیر برای مدیریت داده‌های Key-Value
    val stateMap = remember { mutableStateMapOf("Key1" to "Value1", "Key2" to "Value2") }

    // ساختار اصلی صفحه
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // استفاده از StateFlow برای نمایش مقدار شمارنده
        Text(text = "StateFlow Counter: $counterState")
        Button(onClick = { viewModel.incrementCounter() }) {
            Text("Increment Counter")
        }

        // استفاده از StateFlow برای نمایش و بروزرسانی متن
        BasicTextField(
            modifier = Modifier.size(5.dp).padding(5.dp),
            value = textState,
            onValueChange = { viewModel.updateText(it) }
        )
        Text(text = "StateFlow Text: $textState")

        Spacer(modifier = Modifier.height(16.dp))

        // نمایش متن ذخیره شده در LiveData
        Text(text = "LiveData Text: $liveDataTextState")

        Spacer(modifier = Modifier.height(16.dp))

        // نمایش وضعیت مشتق شده از DerivedStateOf
        Text(text = "Derived State: ${derivedState.value}")

        Spacer(modifier = Modifier.height(16.dp))

        // استفاده از SnapshotStateList برای نمایش و بروزرسانی لیست
        Text("List Items:")
        stateList.forEach {
            Text(it)
        }
        Button(onClick = { stateList.add("New Item ${stateList.size + 1}") }) {
            Text("Add to List")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // استفاده از SnapshotStateMap برای نمایش و بروزرسانی نقشه
        Text("Map Items:")
        stateMap.forEach { (key, value) ->
            Text("$key -> $value")
        }
        Button(onClick = { stateMap["NewKey"] = "NewValue" }) {
            Text("Add to Map")
        }
    }

    // دریافت رویدادها از SharedFlow
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { eventMessage ->
            println("Received Event: $eventMessage")
        }
    }
}
