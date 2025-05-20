package com.example.newsaggregator.ui.util

data class UiEvent<T>(
    private val value: T?,
    private val timestamp: Long = System.currentTimeMillis(),
) {
    private var consumed = false
    fun consume(consumer: (T) -> Unit) {
        if (!consumed) {
            consumed = true
            value?.let { consumer(it) }
        }
    }
}