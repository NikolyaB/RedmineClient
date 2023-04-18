package com.example.redmineclient.user_management

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.domain.user.User
import com.example.redmineclient.presentation.RedOrangeHex

@Composable
fun UserItem(
    user: User,
    onUserClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onTeamLeadClick: (Boolean) -> Unit,
    isTeamLead: Boolean,
    onAddTaskClick: () -> Unit,
    onShowTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color(RedOrangeHex))
            .clickable { onUserClick() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = user.login,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
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
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Лидер команды:",
                fontWeight = FontWeight.Light
            )
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Change status user",
                modifier = Modifier
                    .clickable(MutableInteractionSource(), null) {
                        onTeamLeadClick(!isTeamLead)
                    }
                    .padding(horizontal = 6.dp),
                tint = if (isTeamLead) Color.Green else Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Добавить задачу",
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .clickable(MutableInteractionSource(), null) {
                    onAddTaskClick()
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Показать задачи",
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .clickable(MutableInteractionSource(), null) {
                    onShowTaskClick()
                }
        )
    }
}