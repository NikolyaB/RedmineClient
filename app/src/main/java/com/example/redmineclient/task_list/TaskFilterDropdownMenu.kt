package com.example.redmineclient.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.presentation.RedOrangeHex

@Composable
fun TaskFilterDropdownMenu(
    text: String,
    onTextChanged: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val itemsLabel = listOf("Все задачи", "По приоритету", "Открытые", "Закрытые")


    Row(
        modifier = Modifier.clickable { expanded = !expanded }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "ArrowDropDown",
            modifier = Modifier.size(45.dp),
            tint = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
        ) {
            itemsLabel.forEach { item ->
                val isSelected = item == text
                val style = if (isSelected) {
                    MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(RedOrangeHex)
                    )
                } else {
                    MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        onItemClick(item)
                        onTextChanged(item)
                        expanded = !expanded
                    },
                ) {
                    Text(
                        text = item,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        style = style
                    )
                }
            }
        }
    }
}