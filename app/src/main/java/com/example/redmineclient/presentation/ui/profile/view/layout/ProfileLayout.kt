package com.example.redmineclient.presentation.ui.profile.view.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.redmineclient.presentation.ui.profile.state.ProfileState

@Composable
fun ProfileLayout(
    state: MutableState<ProfileState>
) {
    val user = state.value.userInfo
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        user?.let { Text("Имя: ${it.firstname}") }
        user?.let { Text("Фамилия: ${it.lastname}") }
        user?.let { Text("Почта: ${it.mail}") }
    }
}