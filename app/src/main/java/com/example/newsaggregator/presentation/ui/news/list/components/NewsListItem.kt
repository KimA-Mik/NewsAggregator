package com.example.newsaggregator.presentation.ui.news.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newsaggregator.domain.rss.model.NewsItem
import com.example.newsaggregator.presentation.ui.util.NAPreview
import java.text.DateFormat.getDateTimeInstance


@Composable
fun NewsListItem(
    item: NewsItem,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FirstRow(
                title = item.title,
                publishDate = item.pubDate,
                imageUrl = item.imageUrl,
            )

            Text(
                AnnotatedString.fromHtml(item.description),
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FirstRow(
    title: String,
    publishDate: Long?,
    imageUrl: String?,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    AsyncImage(
        imageUrl, contentDescription = title,
        modifier = Modifier
            .clip(CardDefaults.shape)
            .size(110.dp),

        error = rememberVectorPainter(Icons.Default.Warning),
        contentScale = ContentScale.Crop
    )

//    SimpleDateFormat(
    val dt = getDateTimeInstance()
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        publishDate?.let {
            Text(
                dt.format(it),
                modifier = Modifier.alpha(0.8f),
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun NewsListItemPreview() = NAPreview {
    NewsListItem(
        item = NewsItem(
            guid = "",
            link = "",
            title = "Profound title",
            description = "<b>Hello</b> <i>World</i>",
            pubDate = 0L,
            categories = emptyList(),
            imageUrl = null
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}