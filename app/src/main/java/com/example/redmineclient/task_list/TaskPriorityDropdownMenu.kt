package com.example.redmineclient.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.redmineclient.presentation.RedOrangeHex

@Composable
fun TaskPriorityDropdownMenu(
    onValueChanged: (Long) -> Unit,
    itemLabel: Long,
) {
    var expanded by remember { mutableStateOf(false) }
    val itemsLabel = listOf("Низкий", "Средний", "Высокий")
    val itemText = remember { mutableStateOf("Приоритет") }

    when(itemLabel) {
        "1".toLong() -> itemText.value = "Низкий"
        "2".toLong() -> itemText.value = "Средний"
        "3".toLong() -> itemText.value = "Высокий"
    }

    Row(
        modifier = Modifier.clickable { expanded = !expanded }
    ) {
        Text(
            text = itemText.value,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
        ) {
            itemsLabel.forEach { item ->
                val isSelected = item == itemText.value
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
                        expanded = !expanded
                        itemText.value = item
                        when(item) {
                            "Низкий" -> onValueChanged(1)
                            "Средний" -> onValueChanged(2)
                            "Высокий" -> onValueChanged(3)
                         }
                    }
                ) {
                    Text(
                        text = item,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        style = style
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Choose priority",
            tint = Color.Black
        )
    }
}