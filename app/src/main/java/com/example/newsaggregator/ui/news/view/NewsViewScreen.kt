package com.example.newsaggregator.ui.news.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.newsaggregator.ui.components.BasicWebView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsViewScreenRoot(
    title: String,
    url: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            })
        }) { padding ->
        BasicWebView(
            url = url,
            modifier = Modifier.padding(padding)
        )
    }
}
