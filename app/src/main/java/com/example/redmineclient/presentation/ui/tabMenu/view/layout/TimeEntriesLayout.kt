package com.example.redmineclient.presentation.ui.tabMenu.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.di.StatefulViewModelWrapper
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesInfo
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.BlueDark
import com.example.redmineclient.presentation.theme.BlueLight
import com.example.redmineclient.presentation.theme.White
import com.example.redmineclient.presentation.ui.tabMenu.state.TabMenuState
import com.example.redmineclient.presentation.ui.tabMenu.viewModel.TabMenuViewModel

@Composable
fun TimeEntriesLayout(
    lazyColumnState: LazyListState = rememberLazyListState(),
    viewModelWrapper: StatefulViewModelWrapper<TabMenuViewModel, TabMenuState>,
    state: MutableState<TabMenuState>,
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModelWrapper.viewModel.onCreateTimeEntriesClick() },
            backgroundColor = BlueDark,
            contentColor = White
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }) {
        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            val timeEntries = state.value.timeEntries
            items(items = timeEntries!!) { item ->
                LargeTimeEntries(
                    item,
                    onEditClick = { viewModelWrapper.viewModel.onEditTimeEntriesClick(item) },
                    onDeleteClick = { viewModelWrapper.viewModel.onDeleteTimeEntriesClick(item) }
                )
            }
        }
    }
}

@Composable
fun LargeTimeEntries(
    timeEntries: TimeEntriesInfo,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            ),
        backgroundColor = Blue
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        ) {
            Box (
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .background(BlueLight)
            ) {
                Text(
                    text = "Subject",
                    fontSize = 18.sp,
                    color = BlueDark,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                )
            }
            Text(
                text = "#${timeEntries.id}",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = White,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )

                Text(
                    text = timeEntries.user.name,
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
                Text(
                    text = "${timeEntries.hours}",
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Work,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
                Text(
                    text = timeEntries.activity.name,
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
                Text(
                    text = timeEntries.spent_on,
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    Text(
                        text = "Комментарий",
                        color = White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = timeEntries.comments,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(30))
                            .background(BlueLight)
                            .padding(4.dp)

                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { onEditClick },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = White
                    )
                }
                IconButton(
                    onClick = { onDeleteClick() },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        tint = White
                    )
                }
            }
        }
    }
}