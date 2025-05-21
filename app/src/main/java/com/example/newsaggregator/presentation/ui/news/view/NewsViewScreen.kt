package com.example.newsaggregator.presentation.ui.news.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.newsaggregator.presentation.ui.components.BasicWebView
import com.example.newsaggregator.presentation.ui.util.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsViewScreenRoot(
    title: String,
    url: String,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                })
        }) { padding ->
        BasicWebView(
            url = url,
            modifier = Modifier.padding(padding)
        )
    }
}
