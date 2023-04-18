package com.example.redmineclient.domain.task

import com.example.redmineclient.presentation.*
import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Long?,
    val title: String,
    val content: String,
    val priority: Long,
    val isDone: Boolean,
    val colorHex: Long,
    val created: LocalDateTime,
    val userId: Long?
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}