package com.example.redmineclient.presentation.ui.view.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.get

@Composable
fun ErrorLayout(
    onRefreshClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Ошибка загрузки, повторите попытку"
            )
//            Text(
//                text = res.getString("error_layout.description"),
//                modifier = Modifier.padding(top = res.getDimen("error_layout.description.modifier.padding_top")),
//                style = res.getTextStyle("error_layout.description")
//            )
            Button(
                onClick = onRefreshClick,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Text(
                    text = "Обновить"
                )
            }
        }
    }
}
