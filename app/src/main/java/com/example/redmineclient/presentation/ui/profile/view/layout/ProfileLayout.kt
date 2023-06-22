package com.example.redmineclient.presentation.ui.profile.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.redmineclient.presentation.ui.profile.state.ProfileState

@Composable
fun ProfileLayout(
    state: MutableState<ProfileState>,
    onExitClick: () -> Unit
) {
    val user = state.value.userInfo

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                user?.let {
                    Text(
                        "Имя: ${it.firstname}"
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                user?.let {
                    Text(
                        "Фамилия: ${it.lastname}"
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                user?.let {
                    Text(
                        "Почта: ${it.mail}"
                    )
                }
            }
        }

        Button(
            onClick = { onExitClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
//            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Выйти")
        }
    }
}