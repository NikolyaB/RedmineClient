package com.example.redmineclient.task_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.domain.task.Task
import com.example.redmineclient.domain.time.DateTimeUtil

@Composable
fun TaskItem(
    task: Task,
    backgroundColor: Color,
    onTaskClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDoneClick: (Boolean) -> Unit,
    isDone: Boolean,
    modifier: Modifier = Modifier
) {
    val formattedDate = remember(task.created) {
        DateTimeUtil.formatTaskDate(task.created)
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable { onTaskClick() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = task.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Row {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Change status task",
                    modifier = Modifier
                        .clickable(MutableInteractionSource(), null) {
                            onDoneClick(!isDone)
                        }
                        .padding(horizontal = 15.dp),
                    tint = if (isDone) Color.Green else Color.White
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete task",
                    modifier = Modifier
                        .clickable(MutableInteractionSource(), null) {
                            onDeleteClick()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = task.content,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formattedDate,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.End)
        )

    }
}