package com.example.newsaggregator.presentation.ui.components

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf

@Composable
fun BasicWebView(
    url: String,
    modifier: Modifier = Modifier
) {
    val bundle: Bundle = rememberSaveable { bundleOf() }
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }, modifier = modifier,
        onRelease = { it.saveState(bundle) },
        update = {
            if (bundle.isEmpty) {
                it.loadUrl(url)
                it.settings.javaScriptEnabled = true
            } else {
                it.restoreState(bundle)
            }
        })
}