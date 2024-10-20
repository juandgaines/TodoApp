package com.juandgaines.todoapp.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juandgaines.todoapp.ui.theme.TodoAppTheme

@Composable
fun SummaryInfo(
    modifier: Modifier,
    date: String = "March 9, 2024",
    tasksSummary: String = "5 incomplete, 5 completed"
) {
    Column (
        modifier = modifier
            .padding(16.dp)
    ){
        Text(
            text = date,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = tasksSummary,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun SummaryInfoPreviewLight() {
    TodoAppTheme {
        SummaryInfo(modifier = Modifier)
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
fun SummaryInfoPreviewDark() {
    TodoAppTheme {
        SummaryInfo(modifier = Modifier)
    }
}

