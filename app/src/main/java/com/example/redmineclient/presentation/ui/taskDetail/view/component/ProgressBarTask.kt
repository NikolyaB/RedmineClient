package com.example.redmineclient.presentation.ui.taskDetail.view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarTask(
    done_ratio: Int,
    progress: Double,
    modifier: Modifier,
    textColor: Color,
    lineColor: Color,
    backgroundColor: Color,
    fontWeight: FontWeight,
    cornerRadius: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {

        LinearProgressIndicator(
            modifier = modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius)),
            progress = progress.toFloat(),
            backgroundColor = backgroundColor,
            color = lineColor,
        )

        Text(
            text = "$done_ratio%",
            color = textColor,
            fontWeight = fontWeight
        )
    }
}