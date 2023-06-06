package com.example.redmineclient.presentation.ui.projects.view.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.domain.models.ProjectInfo

@Composable
fun ProjectLayout(
    projects: List<ProjectInfo>,
    onProjectClick: (ProjectInfo) -> Unit
) {
    LazyColumn {
        items(items = projects) { project ->
            ProjectListItem(
                project = project,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                onProjectClick(project)
            }
        }
    }
}

@Composable
fun ProjectListItem(
    project: ProjectInfo,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = modifier
        ) {
            Text(
                text = project.name,
                fontSize = 24.sp
            )
        }
    }
}