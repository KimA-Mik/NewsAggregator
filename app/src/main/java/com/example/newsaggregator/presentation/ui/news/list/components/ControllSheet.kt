package com.example.newsaggregator.presentation.ui.news.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.R
import com.example.newsaggregator.presentation.ui.news.list.model.DisplayCategory

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ControlSheet(
    categories: List<DisplayCategory>,
    onDismissRequest: () -> Unit,
    onSelectChip: (String) -> Unit,
    onClearCategories: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
) = ModalBottomSheet(
    onDismissRequest = onDismissRequest,
    modifier = modifier,
    sheetState = sheetState
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.title_categories),
            style = MaterialTheme.typography.titleLarge
        )

        TextButton(onClick = onClearCategories) {
            Text(stringResource(R.string.action_clear))
        }
    }
    FlowRow(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach {
            FilterChip(
                selected = it.selected,
                onClick = { onSelectChip(it.value) },
                label = { Text(it.value) }
            )
        }
    }
}
