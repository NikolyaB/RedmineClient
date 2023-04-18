package com.example.redmineclient.task_detail

data class TaskDetailState(
    val taskTitle: String = "",
    val isTaskTitleHintVisible: Boolean = false,
    val taskContent: String = "",
    val isTaskContentHintVisible: Boolean = false,
    val taskPriority: Long = -1,
    val isTaskPriorityHintVisible: Boolean = false,
    val taskColor: Long = 0xFFFFFF
)
