package com.example.redmineclient.presentation.ui.projects.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redmineclient.domain.models.ProjectInfo
import com.example.redmineclient.presentation.theme.Blue
import com.example.redmineclient.presentation.theme.White

@Composable
fun ProjectLayout(
    projects: List<ProjectInfo>,
    onProjectClick: (ProjectInfo) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 30.dp)
    ) {
        items(items = projects) { project ->
            ProjectListItem(
                project = project,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Blue)
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
    Box(
        modifier = modifier
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
        ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = project.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "",
                tint = White,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),

            )
        }
    }
}