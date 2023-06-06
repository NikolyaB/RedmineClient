package com.example.redmineclient.presentation.ui.tabMenu.view.layout

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel

@Composable
fun TimeEntriesLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState>,
    state: MutableState<TabMenuState>,
) {
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier.fillMaxSize()
    ) {
        val timeEntries = state.value.timeEntries
        items(items = timeEntries!!) { item ->
            LargeTimeEntries(
                item,
                onClick = { viewModelWrapper.viewModel.onTimeEntriesClick(item) }
            )
        }
    }
}

@Composable
fun LargeTimeEntries(
    timeEntries: TimeEntriesInfo,
    onClick: () -> Unit,
    context: Context = LocalContext.current.applicationContext
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .clickable { Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show(); onClick() }
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            Text("${timeEntries.issue.id}")
            Text(timeEntries.user.name)
            Text("Час(а, ов): ${timeEntries.hours}")
            Text("Деятельность: ${timeEntries.activity.name}")
            Text(
                "Комментарий: ${timeEntries.comments}",
                maxLines = 2
            )
        }
    }
}